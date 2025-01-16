package vn.duantn.sominamshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", columnDefinition = "NVARCHAR(255)")
    private String categoryName;



    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer status;


    public void setStatus(Integer status) {
        this.status = (status == null) ? 0 : status;
    }

}
