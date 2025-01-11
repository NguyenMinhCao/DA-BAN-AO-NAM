package vn.duantn.sominamshop.model.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.ProductDetail;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private long id;

    private double price;

    private long quantity;

    private Long orderId;

    private ProductDetailDTO productDetail;

    public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .orderId(orderDetail.getOrder().getId())
                .productDetail(ProductDetailDTO.toProductDetailDTO(orderDetail.getProductDetail()))
                .build();
    }

}
