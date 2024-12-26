package vn.duantn.sominamshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.repository.CartDetailRepository;
import vn.duantn.sominamshop.repository.CartRepository;
import vn.duantn.sominamshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }


    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product findProductById(long id) {
        Optional<Product> prOptional = this.productRepository.findById(id);
        return prOptional.get();
    }

    public Product findProductByIdWithImg(long id) {
        Optional<Product> prOptional = this.productRepository.findProductWithImages(id);
        return prOptional.get();
    }

}
