package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Pattern;
import vn.duantn.sominamshop.service.PatternService;

@Controller
@RequestMapping("/admin/pattern")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @GetMapping
    public String listPattern(
            @RequestParam(value = "patternName", required = false, defaultValue = "") String patternName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Pattern> patternPage = patternService.getPatterns(patternName, page);
        model.addAttribute("patternPage", patternPage);
        model.addAttribute("patternName", patternName);
        return "admin/pattern/show";
    }

    @GetMapping("/create")
    public String showCreatPattrenForm(Model model) {
        model.addAttribute("newPattern", new Pattern());
        return "admin/pattern/create";
    }

    @PostMapping("/create")
    public String createPattern(@ModelAttribute("newPattern") Pattern newPattern, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/pattern/create";
        }
        if (newPattern.getStatus() == null) {
            newPattern.setStatus(0);
        }
        try {
            patternService.addPattern(newPattern);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/pattern/create";
        }
        return "redirect:/admin/pattern";
    }
    @GetMapping("/edit/{id}")
    public String editPatternForm(@PathVariable Long id, Model model) {
        Pattern pattern = patternService.getPatternById(id);
        model.addAttribute("pattern", pattern);
        return "admin/pattern/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrigin(@PathVariable Long id, @ModelAttribute("pattern") Pattern pattern, RedirectAttributes redirectAttributes, Model model) {
        try {
            patternService.updatePattern(id, pattern);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/admin/pattern";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("pattern", pattern);
            return "admin/pattern/edit";
        }
    }

}
