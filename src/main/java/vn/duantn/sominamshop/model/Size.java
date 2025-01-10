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
    private long id;

    @Column(name = "size_name", columnDefinition = "NVARCHAR(200)")
    private String sizeName;



    @Column(name = "status")
    private Integer status;

    public Integer getStatus() {
        if (status == null) {
            return 0; // Gán mặc định khi là null
        }
        return status;
    }

    public void setStatus(Integer status) {
        if (status == null) {
            this.status = 0; // Gán giá trị 0 nếu status là null
        } else {
            this.status = status;
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<ProductDetail> productDetails;
}
