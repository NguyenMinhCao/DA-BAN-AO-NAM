package vn.duantn.sominamshop.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderHistory;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    @Query("SELECT oh.actionTime FROM OrderHistory oh WHERE oh.order = :order ORDER BY oh.actionTime DESC")
    List<LocalDateTime> findAllTimeByOrder(Order order);

    // @Query("SELECT oh FROM OrderHistory oh WHERE oh.order = :order ORDER BY oh.actionTime DESC")
    // List<OrderHistory> findAllByOrder(Order order);

    @Query(value = "SELECT * FROM order_histories WHERE order_id = :orderId AND CAST(action_time AS DATE) = :date ORDER BY action_time DESC", nativeQuery = true)
    List<OrderHistory> findAllByOrderAndDate(@Param("orderId") Long orderId, @Param("date") LocalDate date);

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.order = :order ORDER BY oh.actionTime DESC")
    List<OrderHistory> findAllByOrderSortedDesc(Order order);

}
