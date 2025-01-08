package vn.duantn.sominamshop.model.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class ProductRestRequest {

    private List<Color> colors;
    private List<Size> sizes;
}
