package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import vn.duantn.sominamshop.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail od WHERE od.id = :idOrderDetail AND od.productDetail.id = :productDetailId")
    void deleteByOrderDetailIdAndProductDetailId(long idOrderDetail, long productDetailId);

}
