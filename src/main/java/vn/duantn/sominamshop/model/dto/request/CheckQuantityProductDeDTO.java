package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckQuantityProductDeDTO {
    private long quantityValue;
    private long orderId;
    private long productDetailID;
    private String description;
    private long orderDetailId;
}
