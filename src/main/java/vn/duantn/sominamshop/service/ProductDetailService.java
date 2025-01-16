package vn.duantn.sominamshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.request.DataGetProductDetail;
import vn.duantn.sominamshop.model.dto.request.ProductDetailRequest;
import vn.duantn.sominamshop.model.dto.response.ProductDetailResponse;
import vn.duantn.sominamshop.model.dto.response.ResProductDetailSearchDTO;
import vn.duantn.sominamshop.model.dto.response.SizeResponse;
import vn.duantn.sominamshop.repository.ProductDetailRepository;
import vn.duantn.sominamshop.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    public List<ProductDetail> getAllProductDetail() {
        return productDetailRepository.findAll();
    }


    public ProductDetail getProductDetailByProductId(Long productId) {
        Product idProduct = productRepository.findById(productId).orElse(null);

        return null;
    }


    public ProductDetail findByProductIdAndColorIdAndSizeId(Integer productId,
            Integer colorId,
            Integer sizeId) {
        return productDetailRepository.findByProductIdAndColorIdAndSizeId(productId, colorId, sizeId);
    }

    public ProductDetail findProductDetailById(Long id) {
        return productDetailRepository.findById(id).orElse(null);
    }

    public List<ProductDetail> getProductDetailsByIds(List<Long> id) {
        return productDetailRepository.findAllById(id);
    }

    public List<ProductDetail> getAll() {
        return productDetailRepository.findAll();
    }

    public List<ProductDetailResponse> getAllByProductId(Long productId) {
        return productDetailRepository.getAllByProductId(productId);
    }

    public List<SizeResponse> getListSizeAddProductDetail(Long productId, Long colorId) {
        return productDetailRepository.getListSizeAddProductDetail(productId, colorId);
    }

    public ProductDetail findById(Long id) {
        return productDetailRepository.findById(id).get();
    }

    public Optional<ProductDetail> findProductDetailById(long id) {
        return this.productDetailRepository.findById(id);
    }

    public void saveProductDetail(ProductDetail productDetail) {
        this.productDetailRepository.save(productDetail);
    }

    public List<Long> findProductDetailByColor(DataGetProductDetail dto) {
        List<ProductDetail> lstPDetail = this.productDetailRepository
                .findProductDetailByColorAndProduct(dto.getIdColor(), dto.getIdProduct());
        List<Long> listSizeId = new ArrayList<>();
        for (ProductDetail pd : lstPDetail) {
            listSizeId.add(pd.getSize().getId());
        }
        return listSizeId;
    }

    public List<Long> findProductDetailBySize(DataGetProductDetail dto) {

        List<ProductDetail> lstPDetail = this.productDetailRepository.findProductDetailBySizeAndProduct(dto.getIdSize(),
                dto.getIdProduct());
        List<Long> listColorId = new ArrayList<>();
        for (ProductDetail pd : lstPDetail) {
            listColorId.add(pd.getColor().getId());
        }
        return listColorId;
    }

    public ProductDetail findProductDetailBySizeAndColorAndProduct(DataGetProductDetail data) {
        ProductDetail findProductDetail = this.productDetailRepository
                .findProductDetailBySizeAndColorAndProduct(data.getIdSize(), data.getIdColor(), data.getIdProduct());
        if (findProductDetail != null) {
            return findProductDetail;
        } else {
            return null;
        }
    }

    @Transactional
    public List<ProductDetail> findProductDetailByProducts(Product product) {
        return this.productDetailRepository.getAllProductDetailByProductId(product.getId());
    }

    @Transactional
    public ProductDetail add(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailRequest.map(new ProductDetail());
        return productDetailRepository.save(productDetail);
    }

    public ProductDetail update(ProductDetail productDetail, Long id) {
        ProductDetail searchProductDetail = productDetailRepository.findById(id).get();
        if (searchProductDetail != null) {
            searchProductDetail.setWeight(productDetail.getWeight());
            searchProductDetail.setQuantity(productDetail.getQuantity());
            searchProductDetail.setPrice(productDetail.getPrice());
            return productDetailRepository.save(searchProductDetail);
        }
        return null;
    }

    public ProductDetail setStatus(Long id) {
        ProductDetail searchProductDetail = productDetailRepository.findById(id).get();
        if (searchProductDetail != null) {
            if (searchProductDetail.getStatus() == 1) {
                searchProductDetail.setStatus(0);
            } else {
                searchProductDetail.setStatus(1);
            }
            return productDetailRepository.save(searchProductDetail);
        }
        return null;
    }

    public int countProduct(int number) {
        return productDetailRepository.countProduct(number);
    }

    public List<ProductDetail> listProduct(int number) {
        return productDetailRepository.listProduct(number);
    }

    public List<ProductDetail> getListProduct() {
        return productDetailRepository.getListProduct();
    }

    public ProductDetail showQuantity(Integer productId, Integer colorId, Integer sizeId) {
        return productDetailRepository.showQuantity(productId, colorId, sizeId);
    }

    public Float getPriceByProductDetail(Integer productId, Integer colorId, Integer sizeId) {
        return productDetailRepository.getPriceByProductDetail(productId, colorId, sizeId);
    }

    @Transactional
    public Map<String, String> updateQuantityProduct(List<OrderDetail> productOrderDetailList) {
        Map<String, String> map = new HashMap<>();
        boolean check = true;
        for(OrderDetail od : productOrderDetailList){
            if(od.getProductDetail() != null){
                Long quantityInSql = productDetailRepository.findQuantityProductById(od.getProductDetail().getId());
                if(quantityInSql == 0){
                    check = false;
                    map.put("error", "opp trong kho còn 0 sản phẩm số :" + od.getProductDetail().getId());
                }else if(quantityInSql < od.getQuantity()){
                    check = false;
                    map.put("error", "Rất xin lỗi khách hàng trong kho còn có "+quantityInSql+" sản phẩm :" + od.getProductDetail().getId());
                }else if(quantityInSql >= od.getQuantity()){
                    check =true;
                }
            }
        }
        if(check == true){
            for(OrderDetail od : productOrderDetailList){
                productDetailRepository.updateQuantityProduct(od.getQuantity(), od.getProductDetail().getId());
            }
        }
        return map;
    }
}
