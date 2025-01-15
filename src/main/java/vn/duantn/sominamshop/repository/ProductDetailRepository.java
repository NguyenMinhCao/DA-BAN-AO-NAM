package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.CounterProductProjection;
import vn.duantn.sominamshop.model.dto.response.ProductDetailResponse;
import vn.duantn.sominamshop.model.dto.response.SizeResponse;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Query(value = "WITH RankedSizes AS (\n" +
            "    SELECT \n" +
            "        [id],\n" +
            "        [size_name],\n" +
            "        ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS sizeRank\n" +
            "    FROM \n" +
            "        [dbo].[sizes]\n" +
            ")\n" +
            "SELECT \n" +
            "    [product_details].[id],\n" +
            "    [colors].[color_name] AS colorName,\n" +
            "    [sizes].[size_name] AS sizeName,\n" +
            "    [product_details].[quantity],\n" +
            "    [product_details].[price],\n" +
            "    [product_details].[status]\n" +
            "FROM \n" +
            "    [dbo].[product_details]\n" +
            "JOIN \n" +
            "    sizes ON product_details.size_id = sizes.id\n" +
            "JOIN \n" +
            "    colors ON product_details.color_id = colors.id\n" +
            "JOIN \n" +
            "    RankedSizes ON sizes.size_name = RankedSizes.size_name\n" +
            "WHERE \n" +
            "    [product_id] = :productId\n" +
            "ORDER BY\n" +
            "    [colors].[color_name],\n" +
            "    RankedSizes.sizeRank;", nativeQuery = true)
    List<ProductDetailResponse> getAllByProductId(@Param("productId") Long productId);

    @Query(value = """
    SELECT sizes.id, 
           sizes.size_name, 
           sizes.status
    FROM sizes
    WHERE NOT EXISTS (
              SELECT 1
              FROM product_details
              WHERE product_details.color_id = :colorId
                    AND product_details.size_id = sizes.id
                    AND product_details.product_id = :productId
          )
""", nativeQuery = true)
    List<SizeResponse> getListSizeAddProductDetail(
            @Param("productId") Integer productId,
            @Param("colorId") Integer colorId
    );


    @Query("Select Count(p.id) From ProductDetail p Where p.quantity < :number")
    int countProduct(int number);

    @Query("SELECT p FROM ProductDetail p WHERE p.quantity < :number ORDER BY p.quantity ASC")
    List<ProductDetail> listProduct(int number);

    @Query("SELECT p FROM ProductDetail p WHERE p.product.status = 0 AND p.quantity > 0")
    List<ProductDetail> getListProduct();

    @Query("select c from ProductDetail c where c.product.id =:productId")
    List<ProductDetail> getAllProductDetailByProductId(@Param("productId") Long productId);

    @Query(value = """
                SELECT
                    pd.*
                FROM
                    product_details pd
                    INNER JOIN
                        products p ON pd.product_id = p.id
                    INNER JOIN
                        colors c ON pd.color_id = c.id
                    INNER JOIN
                        sizes s ON pd.size_id = s.id
                    LEFT JOIN (
                        SELECT product_id, MIN(id) AS min_image_id
                        FROM images
                        GROUP BY product_id
                    ) AS min_images ON pd.product_id = min_images.product_id
                    LEFT JOIN
                        images i ON min_images.min_image_id = i.id
                    WHERE
                        p.id =:productId AND p.status = 0
                        AND pd.size_id =:sizeId AND s.status = 0
                        AND pd.color_id =:colorId AND c.status = 0;
            """, nativeQuery = true)
    ProductDetail findByProductIdAndColorIdAndSizeId(@Param("productId") Integer productId,
            @Param("sizeId") Integer sizeId,
            @Param("colorId") Integer colorId);

    @Query("select pd from ProductDetail pd where pd.id = :productDetailId")
    ProductDetail findByProductDetailId(@Param("productDetailId") Integer productDetailId);

    Optional<ProductDetail> findByProduct_Id(Long productId);

    @Query(value = """
                SELECT pd.*
                FROM cart_details cd
                INNER JOIN product_details pd ON cd.product_detail_id = pd.id
                WHERE cd.id IN :cartDetailId
            """, nativeQuery = true)
    List<ProductDetail> findProductDetailIdByCartDetailId(@Param("cartDetailId") List<Integer> cartDetailId);

    @Query(value = """
                SELECT
                    pd.*
                FROM
                    product_details pd
                JOIN
                    products p ON pd.product_id = p.id
                JOIN
                    sizes s ON pd.size_id = s.id
                JOIN
                    colors c ON pd.color_id = c.id
                WHERE
                    p.id = :productId
                    AND s.id = :sizeId
                    AND c.id = :colorId
            """, nativeQuery = true)
    ProductDetail showQuantity(@Param("productId") Integer productId,
            @Param("colorId") Integer colorId,
            @Param("sizeId") Integer sizeId);

    @Query(value = """
            SELECT
                ROUND(dbo.product_details.price, 0) AS discounted_price
            FROM
                dbo.product_details
            INNER JOIN
                dbo.products ON dbo.product_details.product_id = dbo.products.id
            WHERE
                product_id = :productId AND
                product_details.color_id = :colorId AND
                product_details.size_id = :sizeId
            """, nativeQuery = true)
    Float getPriceByProductDetail(@Param("productId") Integer productId,
            @Param("colorId") Integer colorId,
            @Param("sizeId") Integer sizeId);

    @Query(value = """
            with imagesOrder AS (Select product_detail_id, url_image, ROW_NUMBER() OVER (PARTITION BY product_detail_id ORDER BY (select null)) AS STT from images)
            SELECT pd.id, p.name, pd.quantity, sz.size_name, cl.color_name, pd.price, imagesOrder.url_image as image from product_details pd
            left join products p on p.id = pd.product_id
            left join imagesOrder on pd.id = imagesOrder.product_detail_id AND imagesOrder.STT = 1
            left join colors cl on pd.color_id = cl.id
            left join sizes sz on pd.size_id = sz.id
            WHERE
             (
                    p.name LIKE CONCAT('%', :name, '%')
                    AND (pd.color_id = :color OR :color IS NULL)
                    AND (pd.size_id = :size OR :size IS NULL)
                    AND (p.category_id = :category OR :category IS NULL)
                 )
             AND pd.quantity > 0
            GROUP BY pd.id, p.name, pd.quantity, sz.size_name, cl.color_name, pd.price, imagesOrder.url_image
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM product_details pd
            left join products p on p.id = pd.product_id
            left join colors cl ON pd.color_id = cl.id
            left join sizes sz ON pd.size_id = sz.id
            WHERE
            (
                 (
                   p.name LIKE CONCAT('%', :name, '%')
                    AND (pd.color_id = :color OR :color IS NULL)
                    AND (pd.size_id = :size OR :size IS NULL)
                    AND (p.category_id = :category OR :category IS NULL)
                 )
             )
             AND pd.quantity > 0
            """,nativeQuery = true)
    Page<CounterProductProjection> findAllProductByName(Pageable pageable, @Param(value = "name") String name, @Param("size") Long size, @Param("color") Long color, @Param("category") Long category);

    @Modifying
    @Query("UPDATE ProductDetail p set p.quantity = p.quantity - :quantity WHERE p.id = :id")
    void updateQuantityProduct(@Param("quantity") Long quantity, @Param("id") Long id);

    Page<ProductDetail> findByColorId(Long colorId, Pageable pageable);

    @Query("select pd from ProductDetail pd where pd.color.id =:idColor and pd.product.id = :idProduct")
    List<ProductDetail> findProductDetailByColorAndProduct(long idColor, long idProduct);

    @Query("select pd from ProductDetail pd where pd.size.id =:idSize and pd.product.id = :idProduct")
    List<ProductDetail> findProductDetailBySizeAndProduct(long idSize, long idProduct);

    @Query("select pd from ProductDetail pd where pd.size.id =:idSize and pd.color.id =:idColor and pd.product.id=:idProduct")
    ProductDetail findProductDetailBySizeAndColorAndProduct(long idSize, long idColor, long idProduct);

    @Query("select pd.quantity from ProductDetail pd where pd.id = :id")
    Long findQuantityProductById(@Param("id") Long id);

    List<ProductDetail> findByProductId(Long productId);

}
