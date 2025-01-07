package vn.duantn.sominamshop.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.OrderStaticDTO;
import vn.duantn.sominamshop.service.OrderStatisticService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class OrderStatisticController {

    @Autowired
    private OrderStatisticService orderService;

    @GetMapping("/order/order-statistics")
    public String getOrderStatisticsPage(Model model, @RequestParam(defaultValue = "0") int page) {
        BigDecimal totalRevenue = orderService.getTotalRevenue();
        BigDecimal monthlyRevenue = orderService.getMonthlyRevenue();
        BigDecimal todayRevenue = orderService.getTodayRevenue();
        BigDecimal yearlyRevenue = orderService.getYearlyRevenue();
        Long totalProduct = orderService.getTotalProducts();
//        Long totalLowProduct = orderService.getLowStockProductCount();
        Long totalTodayOrderCount = orderService.getTodayOrderCount();

        totalRevenue = (totalRevenue != null) ? totalRevenue : BigDecimal.ZERO;
        monthlyRevenue = (monthlyRevenue != null) ? monthlyRevenue : BigDecimal.ZERO;
        todayRevenue = (todayRevenue != null) ? todayRevenue : BigDecimal.ZERO;
        yearlyRevenue = (yearlyRevenue != null) ? yearlyRevenue : BigDecimal.ZERO;
        totalProduct = (totalProduct != null) ? totalProduct : 0L;
//        totalLowProduct = (totalLowProduct != null) ? totalLowProduct : 0L;
        totalTodayOrderCount = (totalTodayOrderCount != null) ? totalTodayOrderCount : 0L;

        Pageable pageable = PageRequest.of(page, 5);
//        Page<Product> lowStockProductsPage = orderService.getLowStockProducts(pageable);

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("todayRevenue", todayRevenue);
        model.addAttribute("yearlyRevenue", yearlyRevenue);
        model.addAttribute("totalProduct", totalProduct);
//        model.addAttribute("totalLowProduct", totalLowProduct);
//        model.addAttribute("lowStockProductsPage", lowStockProductsPage);
//        model.addAttribute("lowStockProducts", lowStockProductsPage.getContent());
        model.addAttribute("totalTodayOrderCount", totalTodayOrderCount);

        return "admin/order/order-statistics";
    }
}
