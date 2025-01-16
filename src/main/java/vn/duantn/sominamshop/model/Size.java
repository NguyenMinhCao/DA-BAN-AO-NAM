package vn.duantn.sominamshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sizes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Size extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_name", columnDefinition = "NVARCHAR(200)")
    private String sizeName;



    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer status;


    public void setStatus(Integer status) {
        this.status = (status == null) ? 0 : status;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<ProductDetail> productDetails;
}
