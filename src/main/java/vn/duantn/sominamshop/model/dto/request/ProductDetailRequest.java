package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.Size;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailRequest {
    private Long productId;
    private Long idProductDetail;
    private Integer quantity;
    private BigDecimal price;
    private Integer colorId;
    private Integer sizeId;
    private Integer status;

    public ProductDetail map(ProductDetail productDetail){
        productDetail.setProduct(Product.builder().id(this.productId).build());
        productDetail.setQuantity(this.quantity);
        productDetail.setPrice(this.price);
        productDetail.setColor(Color.builder().id(this.colorId).build());
        productDetail.setSize(Size.builder().id(sizeId).build());
        productDetail.setStatus(this.status);
        return productDetail;
    }
}
