package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.duantn.sominamshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
