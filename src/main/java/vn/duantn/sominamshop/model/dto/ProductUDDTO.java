package vn.duantn.sominamshop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProductUDDTO {
    private Long productId;
    private String productName;
    private List<String> imageUrls;

    public ProductUDDTO(Long productId, String productName, List<String> imageUrls) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrls = imageUrls;
    }

}
