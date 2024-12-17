package vn.duantn.sominamshop.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.dto.*;
import vn.duantn.sominamshop.repository.*;

@Service
public class OrderService {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PromotionService promotionService;
    private final CounterRepository counterRepository;
    private final PromotionRepository promotionRepository;

    public OrderService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            PromotionService promotionService, CounterRepository counterRepository, PromotionRepository promotionRepository) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.promotionService = promotionService;
        this.counterRepository = counterRepository;
        this.promotionRepository = promotionRepository;
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
                order.setPaymentMethod(dto.getPaymentMethod());
                // order.setPaymentMethod(1);
                order.setShippingMethod(dto.getShippingMethod());
                if (dto.getPromotionId() != null) {
                    Promotion promotionById = this.promotionService.findPromotionById(dto.getPromotionId()).get();
                    order.setPromotion(promotionById);
                }
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

    public Page<CounterProductProjection> GetAllProductByName(Pageable pageable, String name) {
        Page<CounterProductProjection> pageCounterRespone = counterRepository.findAllProductByName(pageable, name);
        return pageCounterRespone;
    }

    public Page<UserDTO> GetCustomer(Pageable pageable, String name) {
        Page<User> pageCustomer = userService.findUserByFullNameContainingAndRole(name, Role.builder().id(1).build(),
                pageable);
        Page<UserDTO> pageCustomerDto = pageCustomer.map(user -> UserDTO.toDTO(user));
        return pageCustomerDto;
    }

    @Transactional
    public OrderDTO saveInvoice(Order order) {
        Order orderCreate = orderRepository.save(order);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(orderCreate);
        return orderDTO;
    }

    @Transactional
    public List<OrderDetail> saveInvoiceDetail(List<OrderDetail> list) {
        return orderDetailRepository.saveAll(list);
    }

    @Transactional
    public void updateQuantityProduct(Long quantity, Long id) {
        counterRepository.updateQuantityProduct(quantity, id);
    }

    public List<PromotionDTO> getPromotion(Double orderValue){
        LocalDate today = LocalDate.now();
        List<PromotionDTO> listPromotionDTO = promotionRepository.findByMinOrderValueLessThanEqual(orderValue).stream()
                .map(PromotionDTO::toPromotionDTO)
                .filter(promotionDTO -> promotionDTO.getStartDateAsLocalDate() != null && promotionDTO.getStartDateAsLocalDate().isBefore(today) || promotionDTO.getStartDateAsLocalDate().equals(today))
                .filter(promotionDTO -> promotionDTO.getEndDateAsLocalDate() !=null && promotionDTO.getEndDateAsLocalDate().isAfter(today))
                .filter(promotionDTO -> promotionDTO.getUsageLimit() != null && promotionDTO.getUsageLimit() > 0)
                .filter(promotionDTO -> promotionDTO.isStatus())
                .collect(Collectors.toList());
        return listPromotionDTO;
    }
}
