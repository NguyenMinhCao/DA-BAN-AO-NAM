package vn.duantn.sominamshop.model;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Tên sản phẩm không được để trống!")
    @Column(name = "name", columnDefinition = "NVARCHAR(1500)")
    private String name;


    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Origin origin;


    @ManyToOne
    @JoinColumn(name = "pattern_id")
    private Pattern pattern;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    @Column(name = "description", columnDefinition = "NVARCHAR(3000)")
    private String description;


    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetails;

    @ManyToMany
    @JoinTable(name = "Product_Promotions", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private List<Promotion> promotions;






}