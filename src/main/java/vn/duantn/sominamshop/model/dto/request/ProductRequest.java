package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.model.Product;
@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Integer categoryId;
    private Integer materialId;
    private Integer status;

    public Product map(Product product) {
        product.setName(this.name);
        product.setDescription(this.description);
        product.setCategory(Category.builder().id(this.categoryId).build());
        product.setMaterial(Material.builder().id(this.materialId).build());
        product.setStatus(this.status);
        return product;
    }
}
