package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataAddProductDTO {
    private Integer quantityProduct;
    private long idOrder;
    private long idProductDetail;

}
