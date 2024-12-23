package vn.duantn.sominamshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.id = :productId")
    Optional<Product> findProductWithImages(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category " +
            "JOIN FETCH p.color " +
            "JOIN FETCH p.material " +
            "JOIN FETCH p.origin " +
            "JOIN FETCH p.pattern " +
            "JOIN FETCH p.size " +
            "WHERE p.id = :productId")
    Product findByIdWithRelations(@Param("productId") Long productId);

    Page<Product> findAll(Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String name);


    boolean existsByName(String name);

    @Query(value = """ 
            with imagesOrder AS (Select product_id, image_url, ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY (select null)) AS STT from images)
            SELECT p.id, p.name, p.quantity, sz.size_name, cl.color_name, p.price, imagesOrder.image_url as image from products p
            left join imagesOrder on p.id = imagesOrder.product_id AND imagesOrder.STT = 1
            left join colors cl on p.color_id = cl.id
            left join sizes sz on p.size_id = sz.id 
            WHERE 
            (p.name like CONCAT('%', :name,'%')
            or sz.size_name like CONCAT('%', :name,'%') 
            or cl.color_name like CONCAT('%', :name,'%'))
            and p.quantity > 0    
            GROUP BY p.id, p.name, p.quantity, sz.size_name, cl.color_name, p.price, imagesOrder.image_url
            """,
            countQuery = """
            SELECT COUNT(*) 
            FROM products p 
            left join colors cl ON p.color_id = cl.id
            left join sizes sz ON p.size_id = sz.id 
            WHERE 
            (p.name like CONCAT('%', :name,'%')
            or sz.size_name like CONCAT('%', :name,'%') 
            or cl.color_name like CONCAT('%', :name,'%'))
            and p.quantity > 0  
            """,nativeQuery = true)
    Page<CounterProductProjection> findAllProductByName(Pageable pageable, @Param(value = "name") String name);

    @Modifying
    @Query("UPDATE Product p set p.quantity = p.quantity - :quantity WHERE p.id = :id")
    void updateQuantityProduct(@Param("quantity") Long quantity, @Param("id") Long id);
}
