package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.duantn.sominamshop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
