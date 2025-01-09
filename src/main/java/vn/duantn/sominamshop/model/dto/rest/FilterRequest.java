package vn.duantn.sominamshop.model.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterRequest {
    private List<Color> colors;
    private List<Size> sizes;
    private List<Category> categories;
}
