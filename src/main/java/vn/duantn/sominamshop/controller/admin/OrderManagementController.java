package vn.duantn.sominamshop.controller.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.turkraft.springfilter.boot.Filter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.constants.DiscountType;
import vn.duantn.sominamshop.model.dto.request.AddressUpdateRequest;
import vn.duantn.sominamshop.model.dto.request.CheckQuantityProductDeDTO;
import vn.duantn.sominamshop.model.dto.request.DataAddProductDTO;
import vn.duantn.sominamshop.model.dto.request.DataStatusOrderDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateOrderDetailDTO;
import vn.duantn.sominamshop.model.dto.response.OrderHistoryResponse;
import vn.duantn.sominamshop.model.dto.response.ResultPaginationDTO;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.service.OrderHistoryService;
import vn.duantn.sominamshop.service.OrderManagementService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductDetailService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class OrderManagementController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final OrderManagementService orderManagementService;
    private final ProductDetailService productDetailService;
    private final OrderDetailRepository orderDetailRepository;

    public OrderManagementController(OrderService orderService, OrderHistoryService orderHistoryService,
            OrderManagementService orderManagementService,
            ProductDetailService productDetailService, OrderDetailRepository orderDetailRepository) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.orderManagementService = orderManagementService;
        this.productDetailService = productDetailService;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping("/orders")
    public String getOrders(Model model,
            @Filter Specification<Order> spec,
            @PageableDefault(size = 10) Pageable pageable) {
        ResultPaginationDTO lstOrder = this.orderService.fetchAllOrders(spec, pageable);
        model.addAttribute("lstOrder", lstOrder);
        return "admin/order-management/show";
    }

    @GetMapping("/orders/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {
        Optional<Order> orderById = this.orderService.findOrderById(id);
        if (orderById.isPresent()) {
            List<OrderHistoryResponse> lstOrderHis = this.orderHistoryService
                    .getAllOrderHistoryByOrder(orderById.get());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String formattedDateCreate = orderById.get().getCreatedAt().format(formatter);

            double shippingPrice = 0;
            String paymentMethod = "";
            String shippingMethodString = "";
            if (orderById.get() != null) {
                shippingMethodString = orderById.get().getShippingMethod().toString();
            }

            if (orderById.get() != null) {
                paymentMethod = orderById.get().getPaymentMethod().toString();
            }

            if (orderById.get() != null && shippingMethodString.equals("EXPRESS")) {
                shippingMethodString = "Giao hàng hỏa tốc";
                shippingPrice = 50000;
            } else if (orderById.get() != null && shippingMethodString.equals("FAST")) {
                shippingMethodString = "Giao hàng nhanh";
                shippingPrice = 30000;
            } else {
                shippingPrice = 20000;
                shippingMethodString = "Giao hàng tiết kiệm";
            }

            List<OrderDetail> lstOrderDetail = orderById.get().getOrderDetails();
            double totalPrice = 0;
            for (OrderDetail orderDetail : lstOrderDetail) {
                totalPrice += orderDetail.getPrice();
            }

            if (orderById.get().getCoupon() != null) {
                model.addAttribute("couponCode", orderById.get().getCoupon().getCouponCode());

            }

            // Giả sử totalPrice và shippingPrice là kiểu double
            BigDecimal bdTotalPrice = BigDecimal.valueOf(totalPrice);
            BigDecimal bdShippingPrice = BigDecimal.valueOf(shippingPrice);
            BigDecimal totalAmount = orderById.get().getTotalAmount();

            BigDecimal totalCouponReduce = bdTotalPrice
                    .add(bdShippingPrice)
                    .subtract(totalAmount);

            model.addAttribute("formattedDateCreate", formattedDateCreate);
            model.addAttribute("order", orderById.get());
            model.addAttribute("lstOrderHis", lstOrderHis);
            model.addAttribute("shippingMethodString", shippingMethodString);
            model.addAttribute("shippingPrice", shippingPrice);
            model.addAttribute("paymentMethod", paymentMethod);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCouponReduce", totalCouponReduce);

        }
        // model
        return "admin/order-management/detail";
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Boolean> updateStatusOrder(@PathVariable String id,
            @RequestBody DataStatusOrderDTO dataStatus) {
        Optional<Order> orderById = this.orderService.findOrderById(Long.valueOf(id));
        if (orderById.isPresent()) {
            Order orderGet = orderById.get();
            if (this.orderManagementService.updateStatusOrder(orderGet, dataStatus)) {

                return ResponseEntity.ok().body(true);
            } else {
                return ResponseEntity.ok().body(false);
            }

        }
        return ResponseEntity.ok().body(false);
    }

    @GetMapping("/orders/product-detail/{id}")
    public ResponseEntity<String> findProductDetailId(@PathVariable String id) {
        ProductDetail productDetailById = this.productDetailService.findProductDetailById(Long.valueOf(id));

        if (productDetailById != null) {
            return ResponseEntity.ok().body(productDetailById.getQuantity().toString());
        }
        return null;
    }

    @GetMapping("/orders/{id}/edit")
    public String getEditOrders(@PathVariable String id, Model model) {

        Optional<Order> orderById = this.orderService.findOrderById(Long.valueOf(id));

        if (!orderById.isPresent()) {
            return "error-page";
        }

        Order order = orderById.get();
        model.addAttribute("order", order);

        List<OrderDetail> listOrderDetail = order.getOrderDetails();

        Set<Long> productDetailIdsInOrder = listOrderDetail.stream()
                .map(orderDetail -> orderDetail.getProductDetail().getId())
                .collect(Collectors.toSet());

        List<ProductDetail> allProductDetails = this.productDetailService.getAll();

        List<ProductDetail> availableProductDetails = allProductDetails.stream()
                .filter(productDetail -> !productDetailIdsInOrder.contains(productDetail.getId()))
                .collect(Collectors.toList());

        model.addAttribute("lstProductDetail", availableProductDetails);

        return "admin/order-management/edit";
    }

    @PutMapping("/orders/{id}/edit")
    public ResponseEntity<Void> updateQuantityProductOrderDetail(@PathVariable String id,
            @RequestBody DataUpdateOrderDetailDTO dataUpdate) {
        this.orderService.updateOrderDetail(dataUpdate, id);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/orders/check-quantity-product")
    public ResponseEntity<Boolean> checkQuantityProduct(@RequestBody CheckQuantityProductDeDTO dto) {

        boolean isInStock = this.orderManagementService.checkQuantityProduct(dto);

        return ResponseEntity.ok().body(isInStock);
    }

    @PutMapping("/orders/return-product")
    public ResponseEntity<Boolean> putOrderReturnProduct(@RequestBody CheckQuantityProductDeDTO dto) {
        boolean result = this.orderManagementService.returnProduct(dto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/orders/search")
    public ResponseEntity<?> searchOrderId(@Filter Specification<Order> spec, Pageable pageable) {
        ResultPaginationDTO lstOrder = this.orderService.fetchAllOrders(spec, pageable);
        return ResponseEntity.ok().body(lstOrder);
    }

    @PutMapping("/orders/{id}/note/edit")
    public ResponseEntity<Boolean> putNoteOrder(@PathVariable String id, @RequestBody String textNote) {
        Optional<Order> findOrderById = this.orderService.findOrderById(Long.valueOf(id));
        if (!findOrderById.isPresent()) {
            return ResponseEntity.ok().body(false);
        } else {
            return ResponseEntity.ok().body(this.orderManagementService.updateTextNote(textNote, findOrderById.get()));
        }
    }

    // @PutMapping("/orders/{id}/address/edit")
    // public ResponseEntity<Boolean> putAddressOrder(@PathVariable String id,
    // @RequestBody String textNote) {
    // Optional<Order> findOrderById =
    // this.orderService.findOrderById(Long.valueOf(id));
    // if (!findOrderById.isPresent()) {
    // return ResponseEntity.ok().body(false);
    // } else {
    // findOrderById.get().setNote(textNote);
    // this.orderService.saveOrder(findOrderById.get());
    // return ResponseEntity.ok().body(true);
    // }
    // }

    @PostMapping("/order/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateRequest addressUpdateRequest) {
        Optional<Order> orderById = this.orderService.findOrderById(addressUpdateRequest.getIdOrder());
        if (!orderById.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Địa chỉ không tồn tại.");
        }

        return ResponseEntity.ok()
                .body(this.orderManagementService.updateAddressOrder(addressUpdateRequest, orderById.get()));
    }

    @PostMapping("/orders/add/product")
    public ResponseEntity<?> postMethodName(@RequestBody DataAddProductDTO dto) {

        Optional<Order> orderById = this.orderService.findOrderById(dto.getIdOrder());
        if (orderById.isPresent()) {
            OrderDetail orderDetailNew = new OrderDetail();

            ProductDetail productDetailById = this.productDetailService.findById(dto.getIdProductDetail());
            if (productDetailById != null) {
                orderDetailNew.setProductDetail(productDetailById);
                orderDetailNew.setQuantity(dto.getQuantityProduct());
                double price = dto.getQuantityProduct() * productDetailById.getPrice();
                orderDetailNew.setPrice(price);
            }
            orderDetailNew.setOrder(orderById.get());

            this.orderDetailRepository.save(orderDetailNew);

        }
        return ResponseEntity.ok().body(false);
    }

}
