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
import org.springframework.web.bind.annotation.RequestParam;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.OrderStaticDTO;
import vn.duantn.sominamshop.service.OrderStatisticService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class OrderStatisticController {
    @Autowired
    private OrderStatisticService orderService;
    @GetMapping("admin/order/order-statistic")
    public String getOrderStatistics(Model model, @RequestParam(defaultValue = "0") int page) {
        // Các dữ liệu khác
        BigDecimal totalRevenue = orderService.getTotalRevenue();
        BigDecimal monthlyRevenue = orderService.getMonthlyRevenue();
        BigDecimal todayRevenue = orderService.getTodayRevenue();
        BigDecimal yearlyRevenue = orderService.getYearlyRevenue();
        Long totalProduct = orderService.getTotalProducts();
        Long totalLowProduct = orderService.getLowStockProductCount();


        // Lấy phân trang cho sản phẩm hết hàng
        Pageable pageable = PageRequest.of(page, 5);  // Mỗi trang hiển thị 5 sản phẩm
        Page<Product> lowStockProductsPage = orderService.getLowStockProducts(pageable);
        model.addAttribute("lowStockProductsPage", lowStockProductsPage);
        model.addAttribute("lowStockProducts", lowStockProductsPage.getContent());  //

        // Lấy thông tin thống kê đơn hàng
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2024-01-01");
            Date endDate = sdf.parse("2024-12-31");

            List<OrderStaticDTO> orderStatistics = orderService.getOrderStatisticsByMonth(startDate, endDate);
            String jsonOrderStats = new ObjectMapper().writeValueAsString(orderStatistics);
            model.addAttribute("orderStatisticsJson", jsonOrderStats);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("todayRevenue", todayRevenue);
        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("yearlyRevenue", yearlyRevenue);
        model.addAttribute("totalLowProduct", totalLowProduct);


        return "admin/order/order-statistics";
    }


}
