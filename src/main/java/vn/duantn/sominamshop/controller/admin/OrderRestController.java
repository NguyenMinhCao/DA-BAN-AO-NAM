package vn.duantn.sominamshop.controller.admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.model.dto.OrderDetailDTO;
import vn.duantn.sominamshop.model.dto.CouponDTO;
import vn.duantn.sominamshop.model.dto.rest.FilterRequest;
import vn.duantn.sominamshop.service.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final PromotionService promotionService;
    private final OrderService orderService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final  ProductDetailService productDetailService;

    @GetMapping("/get/products")
    public ResponseEntity<?> GetProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "keyword", defaultValue = "") String search,
            @RequestParam(value = "color", defaultValue = "") Long idColor,
            @RequestParam(value = "size", defaultValue = "") Long idSize,
            @RequestParam(value = "category", defaultValue = "") Long idCategory
    ) {
        System.out.println(idColor + " color" + idCategory + " idcate" + idSize + " idsize");
        Pageable pageable = PageRequest.of(page, limit);
        Page<CounterProductProjection> pageProduct = productService.GetAllProductByName(pageable, search, idSize, idColor, idCategory);
        return ResponseEntity.ok(pageProduct);
    }
    @GetMapping("/get/orders")
    public ResponseEntity<?> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderNonPendingAndPos(DeliveryStatus.COMPLETED, PaymentStatus.PENDING));
    }

    @GetMapping("/get/order/{id}")
    public ResponseEntity<?> getOrderByID(
            @PathVariable(name = "id") Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }
    @GetMapping("/get/orderdetails")
    public ResponseEntity<?> getOrdersDetailByIdOrder(@RequestParam(name="id", defaultValue = "") Long id){
        List<OrderDetailDTO> orderDetailDTOS = orderService.getOrderDetailByOrderId(id);
        orderDetailDTOS.forEach(item -> System.out.println(item.getProductDetail().getProductName()));
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetailByOrderId(id));
    }

    @GetMapping("/get/coupons")
    public ResponseEntity<List<CouponDTO>> getPromotion() {
        List<CouponDTO> promotionDTOList = promotionService.findValidCoupons();
        return ResponseEntity.ok(promotionDTOList);
    }

    @PostMapping("/save/invoice")
    public ResponseEntity<OrderDTO> saveInvoice(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveInvoice(order));
    }

    @PutMapping("/update/invoice")
    public ResponseEntity<?> updateInvoice(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateInvoice(orderDTO));
    }
    @PostMapping("/save/invoice/details")
    public ResponseEntity<List<OrderDetail>> saveInvoiceDetails(@RequestBody List<OrderDetail> request) {
        return ResponseEntity.ok(orderService.saveInvoiceDetails(request));
    }

    @PostMapping("/save/invoice/detail")
    public ResponseEntity<?> saveInvoiceDetail(@RequestBody OrderDetailDTO orderDetail){
        System.out.println(orderDetail.getOrderId() + ": id của orderdetailDto controller");
        return ResponseEntity.ok(orderService.saveInvoiceDetail(orderDetail));
    }

    @PutMapping("/update/invoice/detail")
    public ResponseEntity<?> updateInvoiceDetail(@RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok(orderService.saveInvoiceDetail(orderDetailDTO));
    }

    @DeleteMapping("/delete/invoice/detail/{id}")
    public ResponseEntity<?> deleteInvoiceDetail(@PathVariable Long id){
        try {
            orderService.deleteInvoiceDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chi tiết hóa đơn không tồn tại");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi trong quá trình xóa: " + e.getMessage());
        }
    }
    @GetMapping("/get/filter")
    public ResponseEntity<?> getFilter(){
        List<Category> listCate = categoryService.getAllActive();
        List<Color> listColor = colorService.getAllActive();
        List<Size> listSize = sizeService.getAllActive();
        return ResponseEntity.status(HttpStatus.OK).body(FilterRequest.builder()
                .categories(listCate)
                .colors(listColor)
                .sizes(listSize)
                .build()
        );
    }
    @PutMapping("/update/products")
    public ResponseEntity<?> updateProduct(@RequestBody OrderDetail orderDetail) {
        if (orderDetail != null) {
            productDetailService.updateQuantityProduct(orderDetail.getQuantity(), orderDetail.getProductDetail().getId());
        }
        return ResponseEntity.ok("Cập nhật thành công");
    }
    @PutMapping("/update/coupon")
    public ResponseEntity<?> updateCoupons(
            @RequestParam(value = "quantity", defaultValue = "") Integer quantity,
            @RequestParam(value = "id", defaultValue = "") Long id
    ){
        if(id != null && quantity != null){
            return ResponseEntity.status(HttpStatus.OK).body( promotionService.updateUsageLimitCoupon(quantity, id));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Lỗi khi thay đổi số lượng coupons");
    }
}
