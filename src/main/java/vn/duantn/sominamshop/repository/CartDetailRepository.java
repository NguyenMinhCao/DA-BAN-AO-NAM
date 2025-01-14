package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    CartDetail findCartDetailByCart(Cart cart);

    List<CartDetail> findAllCartDetailByCart(Cart cart);

    CartDetail findCartDetailByCartAndProductDetail(Cart cart, ProductDetail productDetail);

    // CartDetail findCartDetailByProduct(Product product);

}
