package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(value = "select odt from OrderDetail odt " +
            "inner join Order od on od.id = odt.order.id " +
            "left join ProductDetail pd on pd.id = odt.id " +
            "left join Image ig on ig.id = pd.id " +
            "inner join Product p on p.id = pd.product.id " +
            "where od.id = :id")
    List<OrderDetail> getOrderDetailByOrderId(Long id);

//    @Query(value = "select p.id, ig.url_image,  from OrderDetail odt " +
//            "inner join orders od on od.id = odt.order.id " +
//            "left join productDetail pd on pd.id = odt.id " +
//            "left join images ig on ig.id = pd.id " +
//            "inner join products p on p.id = pd.product.id " +
//            "inner join" +
//            "where od.id = :id", nativeQuery = true)
//    List<OrderDetail> getOrderDetailByOrderId(Long id);
}

