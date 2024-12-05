package vn.duantn.sominamshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.repository.CounterRepository;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.repository.OrderRepository;
import vn.duantn.sominamshop.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailsRepo;
    private final CounterRepository counterRepo;
    private final UserRepository usersRepo;

    public Page<CounterProductProjection> GetAllProductByName(Pageable pageable, String name) {
        Page<CounterProductProjection> pageCounterRespone = counterRepo.findAllProductByName(pageable, name);
        return pageCounterRespone;
    }
    public Page<UserDTO> GetCustomer(Pageable pageable, String name) {
        Page<User> pageCustomer = usersRepo.findByFullNameContainingAndRole(name, Role.builder().id(1).build(), pageable);
        Page<UserDTO> pageCustomerDto = pageCustomer.map(user -> UserDTO.toDTO(user));
        return pageCustomerDto;
    }
    @Transactional
    public OrderDTO saveInvoice(Order order){
        Order orderCreate = orderRepo.save(order);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(orderCreate);
        return orderDTO;
    }
    @Transactional
    public List<OrderDetail> saveInvoiceDetail(List<OrderDetail> list){
        return orderDetailsRepo.saveAll(list);
    }
    @Transactional
    public void updateQuantityProduct(Long quantity, Long id){
        counterRepo.updateQuantityProduct(quantity, id);
    }

}
