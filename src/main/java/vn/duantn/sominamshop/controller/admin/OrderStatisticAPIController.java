package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duantn.sominamshop.model.dto.OrderStaticDTO;
import vn.duantn.sominamshop.service.OrderStatisticService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class OrderStatisticAPIController {

    @Autowired
    private OrderStatisticService orderService;

    @GetMapping("/order/order-statistics")
    public List<OrderStaticDTO> getOrderStatistics() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2025-01-01");
            Date endDate = sdf.parse("2025-12-31");
            return orderService.getOrderStatisticsByMonth(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


