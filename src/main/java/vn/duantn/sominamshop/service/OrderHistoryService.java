package vn.duantn.sominamshop.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderHistory;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.response.OrderHistoryResponse;
import vn.duantn.sominamshop.repository.OrderHistoryRepository;
import vn.duantn.sominamshop.util.SecurityUtil;

@Service
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final UserService userService;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository, UserService userService) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.userService = userService;
    }

    public void saveOrderHistory(OrderHistory orderHistory) {
        this.orderHistoryRepository.save(orderHistory);
    }

    public void orderCheckoutHistory(Order order) {
        Optional<User> userByOrder = this.userService.findUserById(order.getUser().getId());
        if (userByOrder.isPresent()) {
            for (int i = 0; i <= 2; i++) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setOrder(order);
                String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                        ? SecurityUtil.getCurrentUserLogin().get()
                        : "";
                if (email != null) {
                    User userByEmail = this.userService.findUserByEmail(email);
                    if (userByEmail.getRole().getName().equals("USER")) {
                        orderHistory.setPerformedBy("Hệ thống");
                    } else {
                        orderHistory.setPerformedBy(userByEmail.getFullName());
                    }
                }
                
                if (i == 0) {
                    orderHistory.setDescription(userByOrder.get().getFullName() + " đặt đơn hàng trên WEBSITE");
                }
                if (i == 1) {
                    orderHistory.setDescription("Email xác nhận đơn hàng đã được gửi tới khách hàng");
                }
                if (i == 2) {
                    BigDecimal totalAmount = order.getTotalAmount();

                    // Sử dụng Locale Việt Nam để phân cách hàng nghìn bằng dấu chấm
                    Locale vietnamLocale = new Locale("vi", "VN");
                    NumberFormat numberFormat = NumberFormat.getInstance(vietnamLocale);

                    // Đảm bảo không sử dụng phần thập phân nếu không cần
                    numberFormat.setMaximumFractionDigits(0);

                    String formattedAmount = numberFormat.format(totalAmount);

                    orderHistory.setDescription("Khoản thanh toán " + formattedAmount
                            + " VND đang chờ xử lý thông qua " + order.getPaymentMethod());
                }
                orderHistoryRepository.save(orderHistory);
            }
        }
    }

    public List<OrderHistoryResponse> getAllOrderHistoryByOrder(Order order) {
        // Danh sách tất cả thời gian hành động
        List<LocalDateTime> lstTimes = this.orderHistoryRepository.findAllTimeByOrder(order);

        // Lấy ra các ngày duy nhất, sắp xếp giảm dần
        List<LocalDate> uniqueDates = lstTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        List<OrderHistoryResponse> listOrderHisResponse = new ArrayList<>();

        // Định dạng dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Định dạng 24 giờ
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (LocalDate timeHandle : uniqueDates) {
            List<OrderHistory> orderHistories = this.orderHistoryRepository.findAllByOrderAndDate(
                    order.getId(),
                    timeHandle);

            // Tạo đối tượng OrderHistoryResponse
            OrderHistoryResponse orderHisResponse = new OrderHistoryResponse();
            List<OrderHistoryResponse.OrderHistoryDetail> arrOrderHisDetail = new ArrayList<>();

            // Định dạng ngày theo "dd/MM/yyyy"
            String formattedDate = timeHandle.format(formatter);
            orderHisResponse.setDate(formattedDate);

            // Duyệt qua các OrderHistory và chuyển đổi sang OrderHistoryDetail
            for (OrderHistory orderHistory : orderHistories) {
                OrderHistoryResponse.OrderHistoryDetail orderHistoryDetail = new OrderHistoryResponse.OrderHistoryDetail();
                orderHistoryDetail.setDescription(orderHistory.getDescription());

                // Định dạng chỉ phần giờ và phút
                String formattedTime = orderHistory.getActionTime().format(timeFormatter);
                orderHistoryDetail.setActionTime(formattedTime);

                orderHistoryDetail.setPerformedBy(orderHistory.getPerformedBy());
                arrOrderHisDetail.add(orderHistoryDetail);
            }

            // Đặt danh sách chi tiết vào OrderHistoryResponse
            orderHisResponse.setOrderHisDetail(arrOrderHisDetail);
            listOrderHisResponse.add(orderHisResponse);
        }

        return listOrderHisResponse;
    }

    // public List<OrderHistory> getAllOrderHistoryByOrder(Order order) {
    // return this.orderHistoryRepository.findAllByOrderSortedDesc(order);
    // }
}
