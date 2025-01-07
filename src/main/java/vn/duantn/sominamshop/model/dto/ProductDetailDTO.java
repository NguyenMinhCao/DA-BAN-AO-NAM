package vn.duantn.sominamshop.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetailDTO {
    private Long productId;
    private Integer colorId;
    private Integer sizeId;

}
