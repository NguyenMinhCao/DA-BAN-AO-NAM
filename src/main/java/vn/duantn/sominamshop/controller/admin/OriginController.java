package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Origin;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.OriginService;

@Controller
@RequestMapping("/admin/origin")
public class OriginController {


    @Autowired
    private OriginService originService;

    @GetMapping
    public String listOrigin(
            @RequestParam(value = "originName", required = false, defaultValue = "") String originName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Origin> originPage = originService.getOrigin(originName, page);
        model.addAttribute("originPage", originPage);
        model.addAttribute("originName", originName);
        return "admin/origin/show";
    }

    @GetMapping("/create")
    public String showCreateOriginForm(Model model) {
        model.addAttribute("newOrigin", new Origin());
        return "admin/origin/create";
    }

    @PostMapping("/create")
    public String createOrigin(@ModelAttribute("newOrigin") Origin newOrigin, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/origin/create";
        }

        if (newOrigin.getStatus() == null) {
            newOrigin.setStatus(0);
        }
        try {
            originService.addOrigin(newOrigin);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/origin/create";
        }
        return "redirect:/admin/origin";
    }
    @GetMapping("/edit/{originId}")
    public String editOriginForm(@PathVariable Long originId, Model model) {
        Origin origin = originService.getOriginById(originId);
        model.addAttribute("origin", origin);
        return "admin/origin/edit";
    }

    @PostMapping("/edit/{originId}")
    public String updateOrigin(@PathVariable Long originId, @ModelAttribute("origin") Origin origin, RedirectAttributes redirectAttributes, Model model) {
        try {
            originService.updateOrigin(originId, origin);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/admin/origin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("origin", origin);
            return "admin/origin/edit";
        }
    }

}
