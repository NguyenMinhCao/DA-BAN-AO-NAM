package vn.duantn.sominamshop.controller.admin;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.request.CheckQuantityProductDeDTO;
import vn.duantn.sominamshop.model.dto.request.DataStatusOrderDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateOrderDetailDTO;
import vn.duantn.sominamshop.model.dto.response.OrderHistoryResponse;
import vn.duantn.sominamshop.service.OrderHistoryService;
import vn.duantn.sominamshop.service.OrderManagementService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductDetailService;
import vn.duantn.sominamshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class OrderManagementController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final OrderManagementService orderManagementService;
    private final ProductService productService;
    private final ProductDetailService productDetailService;

    public OrderManagementController(OrderService orderService, OrderHistoryService orderHistoryService,
            OrderManagementService orderManagementService, ProductService productService,
            ProductDetailService productDetailService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.orderManagementService = orderManagementService;
        this.productService = productService;
        this.productDetailService = productDetailService;
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> lstOrder = this.orderService.getAllOrdersByDeliveryStatusNotNull();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<String> formattedDates = new ArrayList<>();

        for (Order order : lstOrder) {
            if (order.getCreatedAt() != null) {
                String formattedDate = order.getCreatedAt().format(formatter);
                formattedDates.add(formattedDate);
            }
        }

        model.addAttribute("formattedDate", formattedDates);
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

            model.addAttribute("formattedDateCreate", formattedDateCreate);
            model.addAttribute("order", orderById.get());
            model.addAttribute("lstOrderHis", lstOrderHis);
        }
        // model
        return "admin/order-management/detail";
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateStatusOrder(@PathVariable String id,
            @RequestBody DataStatusOrderDTO dataStatus) {
        Optional<Order> orderById = this.orderService.findOrderById(Long.valueOf(id));
        if (orderById.isPresent()) {
            Order orderGet = orderById.get();
            this.orderManagementService.updateStatusOrder(orderGet, dataStatus);
        }
        return ResponseEntity.ok().body(orderById.get());
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
        if (orderById.isPresent()) {
            model.addAttribute("order", orderById.get());
        }
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

}
