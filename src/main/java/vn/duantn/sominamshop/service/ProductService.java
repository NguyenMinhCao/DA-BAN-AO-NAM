package vn.duantn.sominamshop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.repository.CartDetailRepository;
import vn.duantn.sominamshop.repository.CartRepository;
import vn.duantn.sominamshop.repository.ImageRepository;
import vn.duantn.sominamshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ImageRepository imageRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.imageRepository = imageRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
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

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Transactional
    public void updateQuantityProduct(Long quantity, Long id) {
        productRepository.updateQuantityProduct(quantity, id);
    }

    public Page<CounterProductProjection> GetAllProductByName(Pageable pageable, String name) {
        Page<CounterProductProjection> pageCounterRespone = productRepository.findAllProductByName(pageable, name);
        return pageCounterRespone;
    }


    public Page<Product> searchByName(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    }






