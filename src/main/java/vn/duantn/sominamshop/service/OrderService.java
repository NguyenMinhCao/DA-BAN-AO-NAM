package vn.duantn.sominamshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.dto.OrderCheckoutDTO;
import vn.duantn.sominamshop.repository.CartRepository;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.repository.OrderRepository;

@Service
public class OrderService {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> findOrderByUser(User user) {
        return this.orderRepository.findOrderByUser(user);
    }

    public void orderCheckout(HttpSession session, OrderCheckoutDTO dto) {
        String emailUser = (String) session.getAttribute("email");
        User userByEmail = this.userService.findUserByEmail(emailUser);
        if (userByEmail != null) {
            Cart cartByUser = this.cartService.findCartByUser(userByEmail);
            if (cartByUser != null) {
                List<OrderDetail> lstOrderDetails = new ArrayList<>();
                Order order = new Order();
                // create order
                order.setStatus(OrderStatus.PENDING);
                order.setTotalAmount(dto.getTotalAmount());
                order.setUser(userByEmail);
                order.setTotalProducts(cartByUser.getTotalProducts());
                this.orderRepository.save(order);

                // create order Detail
                List<CartDetail> lstCartDetail = cartByUser.getCartDetail();

                for (CartDetail cartDetail : lstCartDetail) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    // save order detail
                    this.orderDetailRepository.save(orderDetail);

                    lstOrderDetails.add(orderDetail);
                    // delete cartDetail
                    this.cartService.deleteCartDetail(cartDetail);
                    session.setAttribute("sum", 0);
                }

                // update order
                order.setOrderDetails(lstOrderDetails);
                this.orderRepository.save(order);

                // delete cart
                this.cartService.deleteCartByUser(userByEmail);

            }
        }
    }
}
