package vn.duantn.sominamshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.repository.CartDetailRepository;
import vn.duantn.sominamshop.repository.CartRepository;

@Service
public class CartService {

    private final ProductService productService;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {
        this.productService = productService;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public void addProductToCart(String email, long idProduct, HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalProducts(0);
            cart.setUser(user);
            this.cartRepository.save(cart);
        }
        Product product = this.productService.findProductById(idProduct);

        // Tìm xem trong cartdetail đã tồn tại cart và product muốn thêm chưa
        CartDetail findCartDetail = this.cartDetailRepository.findCartDetailByCartAndProduct(cart,
                product);

        // Đếm số sản phẩm để set sum vào cart
        List<CartDetail> findLstCartDetail = this.cartDetailRepository.findAllCartDetailByCart(cart);

        if (findCartDetail != null) {
            findCartDetail.setQuantity(findCartDetail.getQuantity() + 1);
            findCartDetail.setPrice(findCartDetail.getQuantity() * product.getPrice());
            int sum = findLstCartDetail.size();
            session.setAttribute("sum", sum);
            cart.setTotalProducts(sum);
            this.cartRepository.save(cart);
            this.cartDetailRepository.save(findCartDetail);
        } else {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetail.setPrice(cartDetail.getQuantity() * product.getPrice());
            int sum = cart.getTotalProducts() + 1;
            session.setAttribute("sum", sum);
            cart.setTotalProducts(sum);
            this.cartRepository.save(cart);
            this.cartDetailRepository.save(cartDetail);
        }
    }

    public void deleteCartByUser(User user) {
        Cart cartByUser = this.cartRepository.findCartByUser(user);
        this.cartRepository.delete(cartByUser);
    }

    public void deleteCartDetail(CartDetail cartDetail) {
        this.cartDetailRepository.delete(cartDetail);
    }

    public void deleteCartDetailByCartAndProduct(String email, Product product, HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        CartDetail cartDetail = this.cartDetailRepository.findCartDetailByCartAndProduct(cart,
                product);
        this.cartDetailRepository.delete(cartDetail);
        List<CartDetail> findLstCartDetail = this.cartDetailRepository.findAllCartDetailByCart(cart);
        int sum = findLstCartDetail.size();
        cart.setTotalProducts(sum);
        this.cartRepository.save(cart);
        session.setAttribute("sum", sum);
    }

    public CartDetail findCartDetailByProduct(Product product) {
        return this.cartDetailRepository.findCartDetailByProduct(product);
    }

    public Cart findCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }

    public CartDetail findCartDetailByCart(Cart cart) {
        return this.cartDetailRepository.findCartDetailByCart(cart);
    }
}
