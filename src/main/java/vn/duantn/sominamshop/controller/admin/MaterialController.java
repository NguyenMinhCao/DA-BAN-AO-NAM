package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.MaterialService;

@Controller
@RequestMapping("/admin/material")
public class MaterialController {


    @Autowired
    private MaterialService materialService;

    @GetMapping
    public String listMaterial(
            @RequestParam(value = "materialName", required = false, defaultValue = "") String materialName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Material> materialPage = materialService.getMaterial(materialName, page);
        model.addAttribute("materialPage", materialPage);
        model.addAttribute("materialName", materialName);
        return "admin/material/show";
    }

    @GetMapping("/create")
    public String showCreateMaterialForm(Model model) {
        model.addAttribute("newMaterial", new Material());
        return "admin/material/create";
    }

    @PostMapping("/create")
    public String createMaterial(@ModelAttribute("newMaterial") Material newMaterial, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/material/create";
        }
        try {
            materialService.addMaterial(newMaterial);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/material/create";
        }
        return "redirect:/admin/material";
    }
    @GetMapping("/edit/{id}")
    public String editMaterialForm(@PathVariable Long id, Model model) {
        Material material = materialService.getMaterialById(id);
        model.addAttribute("material", material);
        return "admin/material/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMaterial(@PathVariable Long id, @ModelAttribute("material") Material material, RedirectAttributes redirectAttributes , Model model) {
        try {
            materialService.updateMaterial(id, material);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/admin/material";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("material", material);
            return "admin/material/edit";
        }
    }

}
