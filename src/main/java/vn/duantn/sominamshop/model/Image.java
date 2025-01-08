package vn.duantn.sominamshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image  extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "url_image", columnDefinition = "NVARCHAR(MAX)")
    private String urlImage;

    @Column(name = "status")
    private Integer status;


    @JsonIgnore
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE) // hoặc CascadeType.ALL nếu muốn quản lý toàn bộ
    private ProductDetail productDetail;



}
