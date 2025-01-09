package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Image;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Integer id;

    private String name;

    private String urlImage;

    private Integer status;

    public static ImageDTO toImageDTO(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .name(image.getName())
                .urlImage(image.getUrlImage())
                .status(image.getStatus())
                .build();
    }
}
