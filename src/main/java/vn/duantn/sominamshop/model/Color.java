package vn.duantn.sominamshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "colors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "color_name", columnDefinition = "NVARCHAR(255)")
    private String colorName;


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
    @OneToMany(mappedBy = "color")
    private List<ProductDetail> productDetails;
}
