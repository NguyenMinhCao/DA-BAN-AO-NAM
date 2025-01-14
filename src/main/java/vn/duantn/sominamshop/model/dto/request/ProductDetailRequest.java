package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.*;

@Getter
@Setter
public class ProductDetailRequest {
    private Long productId;
    private Long idProductDetail;
    private Integer quantity;
    private Float price;
    private Long colorId;
    private Long sizeId;
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

