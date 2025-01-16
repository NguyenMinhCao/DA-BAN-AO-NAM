package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.duantn.sominamshop.model.dto.request.LowStockProductDTO;
import vn.duantn.sominamshop.service.OrderStatisticService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class OrderStatisticController {

    @Autowired
    private OrderStatisticService orderService;

    @GetMapping("/order/order-statistics")
    public String getOrderStatisticsPage(Model model, @RequestParam(defaultValue = "0") int page) {
        // Các dữ liệu thống kê đã có
        BigDecimal totalRevenue = orderService.getTotalRevenue();
        BigDecimal monthlyRevenue = orderService.getMonthlyRevenue();
        BigDecimal todayRevenue = orderService.getTodayRevenue();
        BigDecimal yearlyRevenue = orderService.getYearlyRevenue();
        Long totalProduct = orderService.getTotalProducts();
        Long totalLowProduct = orderService.getLowStockProductCount();
        Long totalTodayOrderCount = orderService.getTodayOrderCount();

        Long pendingOrderCount = orderService.getPendingOrderCount();

        totalRevenue = (totalRevenue != null) ? totalRevenue : BigDecimal.ZERO;
        monthlyRevenue = (monthlyRevenue != null) ? monthlyRevenue : BigDecimal.ZERO;
        todayRevenue = (todayRevenue != null) ? todayRevenue : BigDecimal.ZERO;
        yearlyRevenue = (yearlyRevenue != null) ? yearlyRevenue : BigDecimal.ZERO;
        totalProduct = (totalProduct != null) ? totalProduct : 0L;
        totalLowProduct = (totalLowProduct != null) ? totalLowProduct : 0L;
        totalTodayOrderCount = (totalTodayOrderCount != null) ? totalTodayOrderCount : 0L;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        model.addAttribute("totalRevenue", currencyFormat.format(totalRevenue));
        model.addAttribute("monthlyRevenue", currencyFormat.format(monthlyRevenue));
        model.addAttribute("todayRevenue", currencyFormat.format(todayRevenue));
        model.addAttribute("yearlyRevenue", currencyFormat.format(yearlyRevenue));

        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalLowProduct", totalLowProduct);

        // Phân trang cho sản phẩm còn ít hàng
        Pageable pageable = PageRequest.of(page, 5);
        Page<LowStockProductDTO> lowStockProductsPage = orderService.getLowStockProducts(pageable);
        model.addAttribute("lowStockProductsPage", lowStockProductsPage);
        model.addAttribute("lowStockProducts", lowStockProductsPage.getContent());

        model.addAttribute("totalTodayOrderCount", totalTodayOrderCount);

        // Thêm tổng số đơn hàng đang chờ giải quyết vào model
        model.addAttribute("pendingOrderCount", pendingOrderCount);

        return "admin/order/order-statistics";
    }


}
