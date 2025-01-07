package vn.duantn.sominamshop.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.model.dto.PromotionDTO;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.PromotionService;
import vn.duantn.sominamshop.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final PromotionService promotionService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/get/products")
    public ResponseEntity<?> GetProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "keyword", defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<CounterProductProjection> pageProduct = productService.GetAllProductByName(pageable, search);
        return ResponseEntity.ok(pageProduct);
    }

    @GetMapping("/get/promotions")
    public ResponseEntity<List<PromotionDTO>> getPromotion(
            @RequestParam(name = "orderValue", defaultValue = "10000000") Double orderValue) {
        List<PromotionDTO> promotionDTOList = promotionService.getPromotion(orderValue);
        return ResponseEntity.ok(promotionDTOList);
    }

    @PostMapping("/save/invoice")
    public ResponseEntity<OrderDTO> saveInvoice(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.saveInvoice(order));
    }

    @PostMapping("/save/invoice/details")
    public ResponseEntity<List<OrderDetail>> saveInvoice(@RequestBody List<OrderDetail> request) {
        return ResponseEntity.ok(orderService.saveInvoiceDetail(request));
    }

//    @PutMapping("/update/products")
//    public ResponseEntity<?> saveInvoiceDetail(@RequestBody OrderDetail orderDetail) {
//        if (orderDetail != null) {
//            productService.updateQuantityProduct(orderDetail.getQuantity(), orderDetail.getProduct().getId());
//        }
//        return ResponseEntity.ok("Cập nhật thành công");
//    }
}
