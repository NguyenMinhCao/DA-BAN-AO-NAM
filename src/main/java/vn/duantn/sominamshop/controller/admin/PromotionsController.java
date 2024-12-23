package vn.duantn.sominamshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.repository.PromotionsRepository;

@Controller
public class PromotionsController {

    @Autowired
    private PromotionsRepository promotionsRepository;

    @GetMapping("/hien-thiKM")
    public String hienThiKM(@RequestParam(defaultValue = "0", name = "page") Integer number, Model model){
        Pageable pageable = PageRequest.of(number, 4);
        Page<Promotion> listKM = promotionsRepository.findAll(pageable);
        model.addAttribute("khuyenMaiRequest",new Promotion());
        model.addAttribute("listKM",listKM);
        return "admin/promotions/show";
    }

//    ADD
    @GetMapping("/view-add")
    public String viewAdd() {
        return "admin/promotions/createKM";
    }

    @PostMapping("/khuyen-mai/add")
    public String add(Promotion promotion) {
        promotionsRepository.save(promotion);
        return "redirect:/hien-thiKM";
    }
//REMOVE
    @GetMapping("remove/{id}")
    public String deleteKM(@PathVariable Long id){
        promotionsRepository.deleteById(id);
        return "redirect:/hien-thiKM";
    }
//VIEW
    @RequestMapping("/view-updateKM/{id}")
    public String viewUpdateKM(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionsRepository.findById(id).get();
        model.addAttribute("promo", promotion);
        return "admin/promotions/view_updateKM";
    }
//UPDATE
    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionsRepository.findById(id).get();
        model.addAttribute("promo", promotion);
        return "admin/promotions/updateKM";
    }

    @PostMapping("/khuyen-mai/update/{id}")
    public String update(Promotion promotion) {
        promotionsRepository.save(promotion);
        return "redirect:/hien-thiKM";
    }
}
