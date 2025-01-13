package vn.duantn.sominamshop.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResProductDetailSearchDTO {
    private long id;
    private String name;
    private String urlImage;
    private String colorName;
    private String sizeName;
    private long quantity;
    private double price;
}
