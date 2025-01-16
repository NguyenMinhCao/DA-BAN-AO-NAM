package vn.duantn.sominamshop.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.service.ColorService;

import java.util.List;

@Controller
@RequestMapping("/admin/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public String listColors(
            @RequestParam(value = "colorName", required = false, defaultValue = "") String colorName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Color> colorPage = colorService.getColors(colorName, page);
        boolean isEmpty = colorPage.isEmpty();
        model.addAttribute("colorPage", colorPage);
        model.addAttribute("colorName", colorName);
        model.addAttribute("isEmpty", isEmpty);
        return "admin/color/show";
    }

    @GetMapping("/create")
    public String showCreateColorForm(Model model) {
        model.addAttribute("newColor", new Color());
        return "admin/color/create";
    }

    @PostMapping("/create")
    public String createColor(@ModelAttribute("newColor") Color newColor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/color/create";
        }

        try {
            colorService.addColor(newColor);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/color/create";
        }

        return "redirect:/admin/color";
    }


    @GetMapping("/edit/{id}")
    public String editColorForm(@PathVariable Long id, Model model) {
        Color color = colorService.getColorById(id);
        model.addAttribute("color", color);
        return "admin/color/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateColor(@PathVariable Long id, @ModelAttribute("color") Color color, RedirectAttributes redirectAttributes, Model model) {
        try {
            colorService.updateColor(id, color);
            redirectAttributes.addFlashAttribute("success", "Màu sắc đã được cập nhật thành công!");
            return "redirect:/admin/color";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("color", color);
            return "admin/color/edit";
        }
    }



}

