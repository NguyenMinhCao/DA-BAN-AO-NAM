package vn.duantn.sominamshop.model.dto.response;

public interface ProductResponse {
    Long getId();
    String getName();
    String getImage();
    String getDescription();
    Integer getQuantity();
    Integer getStatus();
}
