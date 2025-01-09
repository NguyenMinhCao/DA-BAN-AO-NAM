package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
        @Query(value = "SELECT " +
                        "YEAR(o.created_at) AS year, " +
                        "MONTH(o.created_at) AS month, " +
                        "COUNT(o.id) AS orderCount, " +
                        "SUM(od.quantity) AS totalQuantity " +
                        "FROM orders o " +
                        "LEFT JOIN order_details od ON o.id = od.order_id " +
                        "WHERE o.created_at BETWEEN :startDate AND :endDate " +
                        "GROUP BY YEAR(o.created_at), MONTH(o.created_at) " +
                        "ORDER BY year, month", nativeQuery = true)
        List<Object[]> getOrderStatisticsByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od")
        BigDecimal getTotalRevenue();

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND FUNCTION('MONTH', o.createdAt) = FUNCTION('MONTH', CURRENT_DATE)")
        BigDecimal getMonthlyRevenue();

        // Tổng doanh thu hôm nay
        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND FUNCTION('DAY', o.createdAt) = FUNCTION('DAY', CURRENT_DATE)")
        BigDecimal getTodayRevenue();

        // Tổng doanh thu năm nay
        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE)")
        BigDecimal getYearlyRevenue();




        @Query("SELECT o FROM Order o WHERE o.deliveryStatus IS NULL AND o.createdBy = :createdBy")
        Order findOrderByDeliveryStatusAndCreatedBy(@Param("createdBy") String createdBy);

        List<Order> findOrderByUser(User user);

//    @Query("SELECT p FROM Product p WHERE p.quantity < 20")
//    Page<Product> findLowStockProducts(Pageable pageable);


        @Query("SELECT o FROM Order o WHERE o.deliveryStatus IS NOT NULL")
        List<Order> findAllOrderByDeliveryStatusNotNull();

    @Query("SELECT COUNT(p) FROM Product p")
    long getTotalProducts();

//    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity < 20")
//    long getLowStockProductCount();


    @Query("SELECT COUNT(o) FROM Order o " +
            "JOIN o.orderDetails od " +
            "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND FUNCTION('DAY', o.createdAt) = FUNCTION('DAY', CURRENT_DATE)")
    long getTodayOrderCount();

    @Query("select od from Order od where od.deliveryStatus = :deliveryStatus and od.orderSource = false and od.paymentStatus = :paymentStatus limit 5")
    List<Order> getAllOrderNonPendingAndPos(@Param("deliveryStatus") DeliveryStatus deliveryStatus, @Param("paymentStatus") PaymentStatus paymentStatus);
}
