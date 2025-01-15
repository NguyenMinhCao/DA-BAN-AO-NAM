package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Order;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.dto.request.LowStockProductDTO;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
        @Query(value = "SELECT " +
                        "YEAR(o.created_at) AS year, " +
                        "MONTH(o.created_at) AS month, " +
                        "COUNT(o.id) AS orderCount, " +
                        "SUM(od.quantity) AS totalQuantity " +
                        "FROM orders o " +
                        "LEFT JOIN order_details od ON o.id = od.order_id " +
                        "WHERE o.payment_status = 'COMPLETED' " +
                        "AND o.delivery_status = 'COMPLETED' " +
                        "AND o.created_at BETWEEN :startDate AND :endDate " +
                        "GROUP BY YEAR(o.created_at), MONTH(o.created_at) " +
                        "ORDER BY year, month", nativeQuery = true)
        List<Object[]> getOrderStatisticsByMonth(
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE o.paymentStatus = 'COMPLETED' " +
                        "AND o.deliveryStatus = 'COMPLETED'")
        BigDecimal getTotalRevenue();

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND FUNCTION('MONTH', o.createdAt) = FUNCTION('MONTH', CURRENT_DATE) " +
                        "AND o.paymentStatus = 'COMPLETED' " +
                        "AND o.deliveryStatus = 'COMPLETED'")
        BigDecimal getMonthlyRevenue();

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND FUNCTION('MONTH', o.createdAt) = FUNCTION('MONTH', CURRENT_DATE) " +
                        "AND FUNCTION('DAY', o.createdAt) = FUNCTION('DAY', CURRENT_DATE) " +
                        "AND o.paymentStatus = 'COMPLETED' " +
                        "AND o.deliveryStatus = 'COMPLETED'")
        BigDecimal getTodayRevenue();

        @Query("SELECT SUM(od.price * od.quantity) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND o.paymentStatus = 'COMPLETED' " +
                        "AND o.deliveryStatus = 'COMPLETED'")
        BigDecimal getYearlyRevenue();

        @Query("SELECT o FROM Order o WHERE o.deliveryStatus IS NULL AND o.createdBy = :createdBy")
        Order findOrderByDeliveryStatusAndCreatedBy(@Param("createdBy") String createdBy);

        List<Order> findOrderByUser(User user);

        @Query("SELECT new vn.duantn.sominamshop.model.dto.request.LowStockProductDTO(pd, p.name) " +
                        "FROM ProductDetail pd " +
                        "JOIN pd.product p " +
                        "WHERE pd.quantity < 20")
        Page<LowStockProductDTO> findLowStockProductsWithName(Pageable pageable);

        @Query("SELECT o FROM Order o WHERE o.deliveryStatus IS NOT NULL")
        List<Order> findAllOrderByDeliveryStatusNotNull();

        @Query("SELECT COUNT(p) FROM ProductDetail p")
        long getTotalProducts();

        @Query("SELECT COUNT(p) FROM ProductDetail p WHERE p.quantity < 20")
        long getLowStockProductCount();

        @Query("SELECT COUNT(o) FROM Order o " +
                        "JOIN o.orderDetails od " +
                        "WHERE FUNCTION('YEAR', o.createdAt) = FUNCTION('YEAR', CURRENT_DATE) " +
                        "AND FUNCTION('DAY', o.createdAt) = FUNCTION('DAY', CURRENT_DATE) " +
                        "AND o.paymentStatus = 'COMPLETED' " +
                        "AND o.deliveryStatus = 'COMPLETED'")
        long getTodayOrderCount();

        @Query(value = "SELECT * FROM orders WHERE CAST(id AS VARCHAR) LIKE CONCAT(:prefix, '%')", nativeQuery = true)
        List<Order> findByIdStartingWith(@Param("prefix") String prefix);

        @Query("select od from Order od where od.deliveryStatus = :deliveryStatus and od.orderSource = false and od.paymentStatus = :paymentStatus")
        List<Order> getAllOrderNonPendingAndPos(@Param("deliveryStatus") DeliveryStatus deliveryStatus,
                        @Param("paymentStatus") PaymentStatus paymentStatus, Pageable page);

        @Query(value = "SELECT o FROM Order o " +
                        "LEFT JOIN User u on u.id = o.user.id " +
                        "LEFT JOIN Coupon cp on cp.id = o.coupon.id " +
                        "WHERE o.id = :id ")
        Optional<Order> getAllOrderById(@Param("id") Long id);
}
