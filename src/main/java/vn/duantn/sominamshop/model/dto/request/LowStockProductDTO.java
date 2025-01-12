package vn.duantn.sominamshop.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.duantn.sominamshop.model.ProductDetail;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LowStockProductDTO {
    private ProductDetail productDetail;
    private String productName;
}
