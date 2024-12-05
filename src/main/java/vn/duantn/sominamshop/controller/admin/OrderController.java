package vn.duantn.sominamshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @GetMapping("")
    public String showIndexPage(){
        return "admin/order/index";
    }
}
