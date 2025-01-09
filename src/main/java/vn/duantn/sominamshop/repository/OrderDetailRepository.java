package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select odt from OrderDetail odt " +
            "inner join Order od on od.id = odt.order.id " +
            "inner join ProductDetail pd on pd.id = odt.productDetail.id " +
            "where od.id = :id")
    List<OrderDetail> getOrderDetailByOrderId(@Param("id") Long id);
}

