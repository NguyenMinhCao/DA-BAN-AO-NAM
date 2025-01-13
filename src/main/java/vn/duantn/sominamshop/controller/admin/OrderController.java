package vn.duantn.sominamshop.controller.admin;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.dto.request.AddressUpdateRequest;
import vn.duantn.sominamshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders/create")
    public String showIndexPage() {
        return "admin/order/index";
    }

    @PostMapping("/admin/order/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateRequest addressUpdateRequest) {
        Optional<Order> orderById = this.orderService.findOrderById(addressUpdateRequest.getIdOrder());
        if (!orderById.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Địa chỉ không tồn tại.");
        }

        orderById.get().setRecipientName(addressUpdateRequest.getFullName());
        orderById.get().setPhoneNumber(addressUpdateRequest.getPhoneNumber());
        orderById.get().setWard(addressUpdateRequest.getWard());
        orderById.get().setDistrict(addressUpdateRequest.getDistrict());
        orderById.get().setCity(addressUpdateRequest.getCity());
        orderById.get().setStreetDetails(addressUpdateRequest.getStreetDetails());
        this.orderService.saveOrder(orderById.get());

        return ResponseEntity.ok().body(orderById);
    }
}
