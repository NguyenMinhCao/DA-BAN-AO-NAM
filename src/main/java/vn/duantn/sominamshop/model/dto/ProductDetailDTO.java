package vn.duantn.sominamshop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.model.dto.request.ImageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetailDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal cost;

    private BigDecimal price;

    private Float weight;

    private Integer status;

    private Size size;

    private Color color;


    private List<ImageDTO> images;

    public static ProductDetailDTO toProductDetailDTO(ProductDetail product) {
        return ProductDetailDTO.builder()
                .id(product.getId())
                .quantity(product.getQuantity())
                .cost(product.getCost())
                .price(product.getPrice())
                .weight(product.getWeight())
                .status(product.getStatus())
                .size(product.getSize())
                .color(product.getColor())
                .images(product.getImages() != null ? product.getImages().stream()
                        .map(ImageDTO::toImageDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
