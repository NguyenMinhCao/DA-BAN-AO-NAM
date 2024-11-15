package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUser(User user);
}