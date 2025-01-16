package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.SizeService;

@Controller
@RequestMapping("/admin/size")

public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public String listColors(
            @RequestParam(value = "sizeName", required = false, defaultValue = "") String sizeName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Size> sizePage = sizeService.getSize(sizeName, page);
        model.addAttribute("sizePage", sizePage);
        model.addAttribute("sizeName", sizeName);
        return "admin/size/show";
    }

    @GetMapping("/create")
    public String showCreateSizeForm(Model model) {
        model.addAttribute("newSize", new Size());
        return "admin/size/create";
    }

    @PostMapping("/create")
    public String createSize(@ModelAttribute("newSize") Size newSize, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/size/create";
        }
        if (newSize.getStatus() == null) {
            newSize.setStatus(0);
        }
        try {
            sizeService.addSize(newSize);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/size/create";
        }
        return "redirect:/admin/size";
    }
    @GetMapping("/edit/{id}")
    public String editSizeForm(@PathVariable Long id, Model model) {
        Size size = sizeService.getSizeById(id);
        model.addAttribute("size", size);
        return "admin/size/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateSize(@PathVariable Long id, @ModelAttribute("size") Size size, RedirectAttributes redirectAttributes, Model model) {
        try {
            sizeService.updateSize(id, size);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/admin/size";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("size", size);
            return "admin/size/edit";
        }
    }


}
