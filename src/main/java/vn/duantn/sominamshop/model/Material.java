package vn.duantn.sominamshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "materials")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "material_name", columnDefinition = "NVARCHAR(255)")
    private String materialName;


    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer status;

    public void setStatus(Integer status) {
        this.status = (status == null) ? 0 : status;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "material")
    private List<Product> products;


}