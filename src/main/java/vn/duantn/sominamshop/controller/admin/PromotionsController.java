package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/view-add")
    public String viewAdd() {
        return "admin/promotions/create";
    }
    @PostMapping("/add")
    public String addKM(Promotion km) {
        promotionsRepository.save(km);
        return "redirect:/hien-thiKM";
    }
}
