package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.model.dto.response.ProductDetailResponse;
import vn.duantn.sominamshop.model.dto.response.ResProductDetailSearchDTO;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.ProductDetailService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.SizeService;

import java.util.List;

import com.turkraft.springfilter.boot.Filter;

@Controller
@RequestMapping("/admin/product-detail")
public class ProductDetailController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ColorService colorService;

    @Autowired
    SizeService sizeService;

    @GetMapping("/{productId}")
    public String getAllByProductId(@PathVariable("productId") Long productId, Model model) {
        List<ProductDetailResponse> lists = productDetailService.getAllByProductId(productId);
        List<Color> listColor = colorService.getAllActive();
        List<Size> listSize = sizeService.getAllActive();
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        model.addAttribute("listColor", listColor);
        model.addAttribute("listSize", listSize);
        model.addAttribute("lists", lists);
        return "admin/product/product-detail";
    }

   

}
