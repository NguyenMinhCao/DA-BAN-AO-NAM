package vn.duantn.sominamshop.service;

import java.util.*;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.model.dto.request.ProductRequest;
import vn.duantn.sominamshop.model.dto.response.ProductResponse;
import vn.duantn.sominamshop.model.dto.response.ResProductDetailSearchDTO;
import vn.duantn.sominamshop.repository.*;

@Service

public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ImageRepository imageRepository;

    private final ProductDetailRepository productDetailRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, ImageRepository imageRepository,
            ProductDetailRepository productDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.imageRepository = imageRepository;
        this.productDetailRepository = productDetailRepository;
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

    public Integer quantityProduct(Long id) {
        Optional<Product> productById = this.productRepository.findById(id);
        Integer quantityProduct = 0;
        for (ProductDetail productDetail : productById.get().getProductDetails()) {
            if (productDetail.getStatus() == 0) {
                quantityProduct += productDetail.getQuantity();
            }
        }
        return quantityProduct;
    }

    // @Transactional
    // public Product saveProduct(Product product) {
    // Product savedProduct = productRepository.save(product);
    //
    // if (product.getVariants() != null) {
    // for (ProductDetail variant : product.getVariants()) {
    // variant.setProduct(savedProduct);
    // productDetailRepository.save(variant);
    // }
    // }
    // return savedProduct;
    // }
    // public Product findProductById(long id) {
    // Optional<Product> prOptional = this.productRepository.findById(id);
    // return prOptional.get();
    // }
    //
    // public Product findProductByIdWithImg(long id) {
    // Optional<Product> prOptional =
    // this.productRepository.findProductWithImages(id);
    // return prOptional.get();
    // }
    ////
    // public boolean existsByName(String name) {
    // return productRepository.existsByName(name);
    // }
    //

    public Page<CounterProductProjection> GetAllProductByName(Pageable pageable, String name, Long size, Long color,
            Long category) {
        Page<CounterProductProjection> pageCounterRespone = productDetailRepository.findAllProductByName(pageable, name,
                size, color, category);
        return pageCounterRespone;
    }
    //
    //
    // public Page<Product> searchByName(String name, Pageable pageable) {
    // return productRepository.findByNameContaining(name, pageable);
    // }
    //
    // public Page<Product> getProductsByColor(Long colorId, Pageable pageable) {
    // return productRepository.findByColorId(colorId, pageable);
    // }
    //
    //
    // public String generateSku(Product product, Size size, Color color) {
    // return product.getId() + "-" + size.getSizeName() + "-" +
    // color.getColorName();
    // }

    public List<ProductResponse> getAll() {
        return productRepository.getAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product add(ProductRequest productRequest) {
        Product product = productRequest.map(new Product());
        return productRepository.save(product);
    }

    public Product update(ProductRequest productRequest, Long id) {
        Product searchProduct = productRepository.findById(id).get();
        if (searchProduct != null) {
            searchProduct = productRequest.map(searchProduct);
            return productRepository.save(searchProduct);
        }
        return null;
    }

    public Product setStatus(Long id) {
        Product searchProduct = productRepository.findById(id).get();
        if (searchProduct != null) {
            if (searchProduct.getStatus() == 1) {
                searchProduct.setStatus(0);
            } else {
                searchProduct.setStatus(1);
            }
            return productRepository.save(searchProduct);
        }
        return null;
    }

    public Integer quantityByColorId(Integer productId, Integer colorId) {
        return productRepository.quantityByColorId(productId, colorId);
    }

    public Integer quantityBySizeId(Integer productId, Integer colorId) {
        return productRepository.quantityBySizeId(productId, colorId);
    }

    public int countOrder() {
        return productRepository.countOrder();
    }

    @Transactional
    public List<Object> listHotSelling(int num) {
        return productRepository.hotSelling(num);
    }

    public List<Product> getListProduct() {
        return productRepository.getListProduct();
    }

    public List<Product> getAllProductDetails() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<Product> pageProducts(Integer pageNo) {
        Pageable page = PageRequest.of(pageNo - 1, 9);
        return productRepository.findAll(page);
    }

    public List<Product> searchProductName(String name) {
        return productRepository.searchProductByName(name);
    }

    public Page<Product> searchProducts(String keyWord, Integer pageNo) {
        List<Product> list = searchProductName(keyWord);
        System.out.println("day ne: " + list);
        Pageable pageable = PageRequest.of(pageNo - 1, 1);
        Integer start = (int) pageable.getOffset();
        Integer end = (start + pageable.getPageSize()) > list.size() ? list.size() : start + pageable.getPageSize();
        list = list.subList(start, end);
        return new PageImpl<>(list, pageable, searchProductName(keyWord).size());
    }

    @Transactional
    public List<ResProductDetailSearchDTO> fetchAllProduct(Specification<Product> spec) {
        List<Product> lstProducts = this.productRepository.findAll(spec);

        List<ResProductDetailSearchDTO> resLst = new ArrayList<>();
        List<ProductDetail> listProductDetails = new ArrayList<>();

        for (Product product : lstProducts) {
            listProductDetails.addAll(product.getProductDetails());
        }

        for (ProductDetail productDetail : listProductDetails) {
            ResProductDetailSearchDTO res = new ResProductDetailSearchDTO();
            res.setId(productDetail.getId());
            res.setName(productDetail.getProduct().getName());
            res.setPrice(productDetail.getPrice());
            res.setSizeName(productDetail.getSize().getSizeName());
            res.setColorName(productDetail.getColor().getColorName());
            res.setUrlImage(
                    productDetail.getImages().size() > 0 ? productDetail.getImages().get(0).getUrlImage() : null);
            res.setQuantity(productDetail.getQuantity());
            resLst.add(res);
        }

        return resLst;
    }

    // public Page<ProductResponseClient> pageProductResponse(Pageable pageable) {
    // return productRepository.pageProductResponse(pageable);
    // }
}
