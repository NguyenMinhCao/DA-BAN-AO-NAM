package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUser(User user);

    @Query("SELECT o FROM Order o WHERE o.status IS NULL AND o.createdBy = :createdBy")
    Order findOrderByStatusAndCreatedBy(@Param("createdBy") String createdBy);

}
