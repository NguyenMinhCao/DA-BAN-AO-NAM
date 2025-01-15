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
    private Long productId;
    private String urlImage;
    private Integer status;
    private Integer isMain;

    public Image map(Image image) {
        if (this.productId == null && this.detailId == null) {
            throw new IllegalArgumentException("Cả Product ID và Detail ID đều không thể là null");
        }

        // Nếu có productId, tạo hoặc cập nhật Product và ProductDetail
        if (this.productId != null) {
            Product product = Product.builder().id(this.productId).build();

            // Nếu có detailId, gán Product vào ProductDetail, nếu không thì tạo ProductDetail mới
            ProductDetail productDetail = this.detailId != null
                    ? ProductDetail.builder().id(this.detailId).product(product).build()
                    : ProductDetail.builder().product(product).build();

            image.setProductDetail(productDetail);
        } else {
            // Nếu chỉ có detailId, tìm ProductDetail tương ứng
            ProductDetail productDetail = ProductDetail.builder().id(this.detailId).build();
            Product product = productDetail.getProduct();

            // Nếu ProductDetail không có Product, tạo mới Product và gán vào ProductDetail
            if (product == null) {
                product = Product.builder().id(this.productId).build();
                productDetail.setProduct(product);
            } else {
                product.setId(this.productId); // Gán lại productId
            }

            image.setProductDetail(productDetail);
        }

        // Cập nhật các trường còn lại của Image
        image.setUrlImage(this.urlImage);
        image.setStatus(this.status);
        image.setIsMain(this.isMain);

        return image;
    }

}

