package vn.duantn.sominamshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OrderController {
    @GetMapping("/admin/orders/create")
    public String showIndexPage() {
        return "admin/order/index";
    }
}
