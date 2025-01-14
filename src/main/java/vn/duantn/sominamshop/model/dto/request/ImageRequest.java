package vn.duantn.sominamshop.model.dto.request;


import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;


@Getter
@Setter
public class ImageRequest {
    private Long detailId;
    private String urlImage;
    private Integer status;
    private Integer isMain;

    public Image map(Image image) {
        if (this.detailId == null) {
            throw new IllegalArgumentException("ProductDetail ID không thể là null");
        }
        ProductDetail productDetail = ProductDetail.builder().id(this.detailId).build();
        image.setProductDetail(productDetail);
        image.setUrlImage(this.urlImage);
        image.setStatus(this.status);
        image.setIsMain(this.isMain);
        return image;
    }

}


