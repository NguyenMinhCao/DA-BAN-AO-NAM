package vn.duantn.sominamshop.model.dto.request;


import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;

@Getter
@Setter
public class ImageRequest {
    private Long productId;
    private String urlImage;
    private Integer status;

    public Image map(Image image){
        image.setProduct(Product.builder().id(this.productId).build());
        image.setUrlImage(this.urlImage);
        image.setStatus(this.status);
        return image;
    }
    }

