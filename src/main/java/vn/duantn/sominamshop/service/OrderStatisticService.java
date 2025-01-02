package vn.duantn.sominamshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.OrderStaticDTO;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderStatisticService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderStatisticService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderStaticDTO> getOrderStatisticsByMonth(Date startDate, Date endDate) {
        List<Object[]> results = orderRepository.getOrderStatisticsByMonth(startDate, endDate);
        System.out.println("Results: " + results);
        List<OrderStaticDTO> orderDTOs = new ArrayList<>();

        Map<Integer, Long> orderCounts = new HashMap<>();
        Map<Integer, BigDecimal> monthlyTotals = new HashMap<>();
        for (Object[] row : results) {
            int year = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            long orderCount = ((Number) row[2]).longValue();
            BigDecimal totalQuantity = (row[3] != null)
                    ? new BigDecimal(row[3].toString())
                    : BigDecimal.ZERO;
            orderCounts.put(month, orderCounts.getOrDefault(month, 0L) + orderCount);
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, BigDecimal.ZERO).add(totalQuantity));
        }
        for (int i = 1; i <= 12; i++) {
            long orderCount = orderCounts.getOrDefault(i, 0L);
            BigDecimal totalQuantity = monthlyTotals.getOrDefault(i, BigDecimal.ZERO);

            OrderStaticDTO orderDTO = new OrderStaticDTO(i, orderCount, totalQuantity);
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

    public BigDecimal getYearlyRevenue() {
        return orderRepository.getYearlyRevenue();
    }

    public long getTotalProducts() {
        return orderRepository.getTotalProducts();
    }

    public long getLowStockProductCount() {
        return orderRepository.getLowStockProductCount();
    }

    public long getTodayOrderCount() { return orderRepository.getTodayOrderCount(); }
}
