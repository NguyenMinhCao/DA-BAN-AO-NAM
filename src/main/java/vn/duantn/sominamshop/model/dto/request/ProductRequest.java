package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.*;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long materialId;
    private Long patternId;
    private Long originId;
    private Integer status;

    public Product map(Product product) {
        product.setName(this.name);
        product.setDescription(this.description);
        product.setCategory(Category.builder().id(this.categoryId).build());
        product.setMaterial(Material.builder().id(this.materialId).build());
        product.setPattern(Pattern.builder().id(this.patternId).build());
        product.setOrigin(Origin.builder().originId(this.originId).build());
        product.setStatus(this.status);
        return product;
    }
}
