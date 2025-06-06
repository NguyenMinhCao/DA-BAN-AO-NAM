package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.model.Origin;
import vn.duantn.sominamshop.model.Pattern;
import vn.duantn.sominamshop.service.*;

import java.util.List;

@Controller
@RequestMapping("/admin/product/add")
public class AddProductController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PatternService patternService;

    @Autowired
    OriginService originService;

    @Autowired
    ColorService colorService;

    @Autowired
    MaterialService materialService;

    @Autowired
    SizeService sizeService;

    @GetMapping("")
    public String getAll(Model model) {

        List<Category> listCategory = categoryService.getAllActive();
        model.addAttribute("listCategory", listCategory);

        // List<Color> listColor = colorService.getAllActive();
        // model.addAttribute("listColor", listColor);

        List<Material> listMaterial = materialService.getAllActive();
        model.addAttribute("listMaterial", listMaterial);

        List<Origin> listOrigin = originService.getAllActive();
        model.addAttribute("listOrigin", listOrigin);

        List<Pattern> listPattern = patternService.getAllActive();
        model.addAttribute("listPattern", listPattern);

        // List<Size> listSize = sizeService.getAllActive();
        // model.addAttribute("listSize", listSize);
        return "admin/product/add";
    }
}
