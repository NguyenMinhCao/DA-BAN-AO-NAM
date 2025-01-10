package vn.duantn.sominamshop.model.dto;


import lombok.*;
import vn.duantn.sominamshop.model.*;

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
    private String productName;
    private BigDecimal price;

    private Float weight;

    private Integer status;

    private String sizeName;

    private String colorName;


    private List<ImageDTO> images;

    public static ProductDetailDTO toProductDetailDTO(ProductDetail product) {
        if(product == null){
            return null;
        }
        return ProductDetailDTO.builder()
                .id(product.getId())
                .quantity(product.getQuantity())
                .cost(product.getCost())
                .productName(product.getProduct().getName())
                .price(product.getPrice())
                .weight(product.getWeight())
                .status(product.getStatus())
                .sizeName(product.getSize().getSizeName())
                .colorName(product.getColor().getColorName())
                .images(product.getImages() != null ? product.getImages().stream()
                        .map(ImageDTO::toImageDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
