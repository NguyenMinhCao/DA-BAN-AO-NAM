package vn.duantn.sominamshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.repository.CartDetailRepository;
import vn.duantn.sominamshop.repository.CartRepository;
import vn.duantn.sominamshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public List<CartDetail> getAllProductByUser(String email) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        return this.cartDetailRepository.findAllCartDetailByCart(cart);
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product findProductById(long id) {
        Optional<Product> prOptional = this.productRepository.findById(id);
        return prOptional.get();
    }

    public Product findProductByIdWithImg(long id) {
        Optional<Product> prOptional = this.productRepository.findProductWithImages(id);
        return prOptional.get();
    }

    public void addProductToCart(String email, long idProduct, HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setSum(0);
            cart.setUser(user);
            this.cartRepository.save(cart);
        }
        Product product = this.findProductById(idProduct);

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
            cart.setSum(sum);
            this.cartRepository.save(cart);
            this.cartDetailRepository.save(findCartDetail);
        } else {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetail.setPrice(cartDetail.getQuantity() * product.getPrice());
            int sum = cart.getSum() + 1;
            session.setAttribute("sum", sum);
            cart.setSum(sum);
            this.cartRepository.save(cart);
            this.cartDetailRepository.save(cartDetail);
        }
    }

    public CartDetail findCartDetailByProduct(Product product) {
        return this.cartDetailRepository.findCartDetailByProduct(product);
    }

    public void deteleCartDetail(String email, Product product, HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        CartDetail cartDetail = this.cartDetailRepository.findCartDetailByCartAndProduct(cart,
                product);
        this.cartDetailRepository.delete(cartDetail);
        List<CartDetail> findLstCartDetail = this.cartDetailRepository.findAllCartDetailByCart(cart);
        int sum = findLstCartDetail.size();
        cart.setSum(sum);
        this.cartRepository.save(cart);
        session.setAttribute("sum", sum);
    }
}
