package vn.duantn.sominamshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Product;

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

}
