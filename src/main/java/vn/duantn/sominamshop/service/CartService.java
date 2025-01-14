package vn.duantn.sominamshop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.constants.ShippingMethod;
import vn.duantn.sominamshop.model.dto.CartDetailUpdateRequestDTO;
import vn.duantn.sominamshop.model.dto.request.DataGetProductDetail;
import vn.duantn.sominamshop.repository.CartDetailRepository;
import vn.duantn.sominamshop.repository.CartRepository;

@Service
public class CartService {

    private final ProductService productService;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderService orderService;
    private final AddressService addressService;
    private final CouponService promotionService;
    private final ProductDetailService productDetailService;

    public CartService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, @Lazy OrderService orderService, AddressService addressService,
            CouponService promotionService, ProductDetailService productDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderService = orderService;
        this.addressService = addressService;
        this.promotionService = promotionService;
        this.productDetailService = productDetailService;
    }

    public void addProductDetailToCart(String email, DataGetProductDetail data, HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalProducts(0);
            cart.setUser(user);
            this.cartRepository.save(cart);

            // tạo mới một order phục vụ cho bên order không liên quan đến cart
            Order order = new Order();
            order.setPaymentMethod("COD");
            order.setShippingMethod(ShippingMethod.SAVE);
            order.setOrderStatus(OrderStatus.PENDING_INVOICE);
            List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);
            for (Address address : arrAddressByUser) {
                if (address.isStatus() == true) {
                    order.setAddress(address);
                }
            }
            this.orderService.saveOrder(order);
        }

        ProductDetail productDetail = this.productDetailService.findProductDetailBySizeAndColorAndProduct(data);
        // Product product = this.productService.findProductById(idProduct);

        // Tìm xem trong cartdetail đã tồn tại cart và product muốn thêm chưa
        CartDetail findCartDetail = this.cartDetailRepository.findCartDetailByCartAndProductDetail(cart,
                productDetail);

        // Đếm số sản phẩm để set sum vào cart
        List<CartDetail> findLstCartDetail = this.cartDetailRepository.findAllCartDetailByCart(cart);

        if (findCartDetail != null) {
            findCartDetail.setQuantity(findCartDetail.getQuantity() + 1);
            findCartDetail.setPrice(findCartDetail.getQuantity() * productDetail.getPrice());
            int sum = findLstCartDetail.size();
            session.setAttribute("sum", sum);
            cart.setTotalProducts(sum);
            this.cartRepository.save(cart);
            this.cartDetailRepository.save(findCartDetail);
        } else {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProductDetail(productDetail);
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetail.setPrice(cartDetail.getQuantity() * productDetail.getPrice());
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

    public void saveCartDetail(CartDetail cartDetail) {
        this.cartDetailRepository.save(cartDetail);
    }

    public void deleteCartDetailByCartAndProduct(String email, ProductDetail productDetail,
            HttpSession session) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        CartDetail cartDetail = this.cartDetailRepository.findCartDetailByCartAndProductDetail(cart,
                productDetail);
        this.cartDetailRepository.delete(cartDetail);
        List<CartDetail> findLstCartDetail = this.cartDetailRepository.findAllCartDetailByCart(cart);
        int sum = findLstCartDetail.size();
        cart.setTotalProducts(sum);
        this.cartRepository.save(cart);
        if (sum == 0) {
            Order order = this.orderService.findOrderByStatusAndCreatedBy();
            if (order != null) {
                this.orderService.deleteOrder(order);
            }
            this.cartRepository.delete(cart);
        }
        session.setAttribute("sum", sum);
    }

    public List<CartDetail> getAllCartDetailByCart(String email) {
        User user = this.userService.findUserByEmail(email);
        Cart cart = this.cartRepository.findCartByUser(user);
        return this.cartDetailRepository.findAllCartDetailByCart(cart);
    }

    // public CartDetail findCartDetailByProduct(Product product) {
    // return this.cartDetailRepository.findCartDetailByProduct(product);
    // }

    public Cart findCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }

    public CartDetail findCartDetailByCart(Cart cart) {
        return this.cartDetailRepository.findCartDetailByCart(cart);
    }

    public Optional<CartDetail> findCartDetailById(Long id) {
        return this.cartDetailRepository.findById(id);
    }

    public Map<String, Object> updateCartDetailProductQuantity(CartDetailUpdateRequestDTO dto, HttpSession session) {
        String emailUser = (String) session.getAttribute("email");
        Map<String, Object> response = new HashMap<>();
        Order order = this.orderService.findOrderByStatusAndCreatedBy();

        long cartDetailId = dto.getCartDetailId();
        int quantity = dto.getQuantity();

        CartDetail cartDetailById = this.findCartDetailById(cartDetailId).get();
        if (cartDetailById != null) {
            cartDetailById.setQuantity(quantity);
            cartDetailById.setPrice(cartDetailById.getProductDetail().getPrice() * quantity);
            this.saveCartDetail(cartDetailById);
        }

        List<CartDetail> lstCartDetail = this.getAllCartDetailByCart(emailUser);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetail) {
            totalPrice += cartDetail.getPrice();
        }

        double shippingPrice = 0;
        double discountValue = 0;
        double totalPayment = 0;

        if (order.getShippingMethod().equals("EXPRESS")) {
            shippingPrice = 50000;
        } else if (order.getShippingMethod().equals("FAST")) {
            shippingPrice = 30000;
        } else {
            shippingPrice = 20000;
        }

        if (order.getCoupon() != null) {
            discountValue = order.getCoupon().getDiscountValueFixed();
        }

        totalPayment = totalPrice + shippingPrice - discountValue;

        response.put("totalPayment", totalPayment);
        return response;
    }
}
