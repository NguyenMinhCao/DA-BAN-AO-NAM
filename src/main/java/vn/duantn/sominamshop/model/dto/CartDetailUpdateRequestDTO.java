package vn.duantn.sominamshop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailUpdateRequestDTO {
    private Long cartDetailId;
    private Integer quantity;
}
