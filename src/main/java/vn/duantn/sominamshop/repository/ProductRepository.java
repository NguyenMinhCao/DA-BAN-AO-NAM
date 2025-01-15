package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.dto.response.ProductResponse;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


        // @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.id =
        // :productId")
        // Optional<Product> findProductWithImages(@Param("productId") Long productId);
        //
        // @Query("SELECT p FROM Product p " +
        // "JOIN FETCH p.category " +
        // "JOIN FETCH p.color " +
        // "JOIN FETCH p.material " +
        // "JOIN FETCH p.origin " +
        // "JOIN FETCH p.pattern " +
        // "JOIN FETCH p.size " +
        // "WHERE p.id = :productId")
        // Product findByIdWithRelations(@Param("productId") Long productId);
        //
        // Page<Product> findAll(Pageable pageable);
        //
        // Page<Product> findByNameContaining(String name, Pageable pageable);
        //
        // boolean existsByName(String name);

        // Page<CounterProductProjection> findAllProductByName(Pageable pageable,
        // @Param(value = "name") String name);
        //
        // @Modifying
        // @Query("UPDATE Product p set p.quantity = p.quantity - :quantity WHERE p.id =
        // :id")
        // void updateQuantityProduct(@Param("quantity") Long quantity, @Param("id")
        // Long id);
        //
        // Page<Product> findByColorId(Long colorId, Pageable pageable);



        List<Product> findByName(String name);

        @Query(value = "SELECT\n" +
                        "    p.id AS id,\n" +
                        "    p.name AS name,\n" +
                        "    COALESCE(i.url_image, '') AS image,\n" +
                        "    p.description AS description,\n" +
                        "    SUM(pd.quantity) AS quantity,\n" +
                        "    p.status AS status\n" +
                        "FROM\n" +
                        "    products p\n" +
                        "LEFT JOIN\n" +
                        "    product_details pd ON p.id = pd.product_id\n" +
                        "LEFT JOIN\n" +
                        "    images i ON pd.id = i.product_detail_id AND i.is_main = 1\n" +
                        "GROUP BY\n" +
                        "    p.id, p.name, p.description, p.status, i.url_image\n" +
                        "ORDER BY p.id", nativeQuery = true)
        List<ProductResponse> getAll();

        @Query(value = "SELECT SUM(dbo.product_details.quantity)\n" +
                        "FROM     dbo.colors INNER JOIN\n" +
                        "                  dbo.product_details ON dbo.colors.id = dbo.product_details.color_id INNER JOIN\n"
                        +
                        "                  dbo.products ON dbo.product_details.product_id = dbo.products.id \n" +
                        "WHERE dbo.products.id = :productId and dbo.colors.id = :colorId", nativeQuery = true)
        Integer quantityByColorId(@Param("productId") Integer productId, @Param("colorId") Integer colorId);

        @Query(value = "SELECT SUM(dbo.product_details.quantity)\n" +
                        "FROM     dbo.sizes INNER JOIN\n" +
                        "                  dbo.product_details ON dbo.sizes.id = dbo.product_details.size_id INNER JOIN\n"
                        +
                        "                  dbo.products ON dbo.product_details.product_id = dbo.products.id \n" +
                        "WHERE dbo.products.id = :productId and dbo.sizes.id = :sizeId", nativeQuery = true)
        Integer quantityBySizeId(@Param("productId") Integer productId, @Param("sizeId") Integer sizeId);

        @Query("Select Count(p.id) From Product p")
        int countOrder();

        @Procedure(name = "hotSelling")
        List<Object> hotSelling(@Param("minQuantity") int minQuantity);

        @Query("Select p From Product p")
        List<Product> getListProduct();

        @Query("select c from Product c where c.name like %:name% and c.status = 0")
        List<Product> searchProductByName(@Param("name") String name);

        // @Query(value = """
        // SELECT
        // new vn.duantn.sominamsh.o.model.response.ProductResponseClient(
        // p.id,
        // p.name,
        // COALESCE((SELECT MAX(i.urlImage) FROM Image i WHERE i.product.id = p.id),
        // ''),
        // p.description,
        // MIN(pd.price) as minPrice,
        // MAX(pd.price) as maxPrice,
        // p.status,
        // 0 AS discount
        // )
        // FROM
        // Product p
        // LEFT JOIN
        // p.productDetails pd
        // LEFT JOIN
        // p.images i
        // WHERE
        // i.id = (SELECT MIN(id) FROM Image WHERE product.id = p.id) AND
        // p.status = 0
        // GROUP BY
        // p.id, p.name, p.description, p.status
        // ORDER BY
        // p.id
        // """)
        // Page<ProductResponseClient> pageProductResponse(Pageable pageable);
}
