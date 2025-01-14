package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataUpdateOrderDetailDTO {
    private long productDetailId;
    private long quantity;
    private boolean updateORemove;
    private boolean restocking;
}
