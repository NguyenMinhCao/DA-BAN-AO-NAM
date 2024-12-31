package vn.duantn.sominamshop.controller.admin;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.service.OrderService;

@Controller
@RequestMapping("/admin")
public class OrderManagementController {

    private final OrderService orderService;

    public OrderManagementController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> lstOrder = this.orderService.getAllOrdersByStatusNotNull();

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
    public String getOrderDetail(@PathVariable Long id) {
        Optional<Order> orderById = this.orderService.findOrderById(id);
        if (orderById.isPresent()) {

        }
        return "admin/order-management/detail";
    }

}
