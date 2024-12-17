package vn.duantn.sominamshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.repository.OrderRepository;
import vn.duantn.sominamshop.repository.PatternRepository;
import vn.duantn.sominamshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderStatisticService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderStatisticService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }


        public List<OrderDTO> getOrderStatisticsByMonth(Date startDate, Date endDate) {
        // Lấy kết quả từ repository
        List<Object[]> results = orderRepository.getOrderStatisticsByMonth(startDate, endDate);

        // In kết quả ra console để kiểm tra
        System.out.println("Results: " + results);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        // Tạo map để lưu tổng số lượng và số đơn hàng cho từng tháng
        Map<Integer, Long> orderCounts = new HashMap<>();
        Map<Integer, BigDecimal> monthlyTotals = new HashMap<>();

        // Xử lý kết quả từ repository
        for (Object[] row : results) {
            // Đọc các giá trị từ Object[] đảm bảo đúng kiểu dữ liệu
            int year = ((Number) row[0]).intValue();   // Năm
            int month = ((Number) row[1]).intValue();  // Tháng
            long orderCount = ((Number) row[2]).longValue(); // Tổng số đơn hàng
            BigDecimal totalQuantity = (row[3] != null)
                    ? new BigDecimal(row[3].toString())
                    : BigDecimal.ZERO; // Tổng số lượng sản phẩm

            // Cộng dồn tổng số lượng và số đơn hàng vào map
            orderCounts.put(month, orderCounts.getOrDefault(month, 0L) + orderCount);
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, BigDecimal.ZERO).add(totalQuantity));
        }

        // Tạo OrderDTO cho từng tháng từ 1 đến 12
        for (int i = 1; i <= 12; i++) {
            long orderCount = orderCounts.getOrDefault(i, 0L);
            BigDecimal totalQuantity = monthlyTotals.getOrDefault(i, BigDecimal.ZERO);

            // Tạo DTO cho tháng hiện tại
            OrderDTO orderDTO = new OrderDTO(i, orderCount, totalQuantity);
            orderDTOs.add(orderDTO);
        }

        return orderDTOs;
    }


    public Page<Product> getLowStockProducts(Pageable pageable) {
        return orderRepository.findLowStockProducts(pageable);
    }


    public BigDecimal getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    public BigDecimal getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue();
    }


    public BigDecimal getTodayRevenue() {
        return orderRepository.getTodayRevenue();
    }

    // Lấy doanh thu năm nay
    public BigDecimal getYearlyRevenue() {
        return orderRepository.getYearlyRevenue();
    }
}
