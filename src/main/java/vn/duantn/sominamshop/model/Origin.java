package vn.duantn.sominamshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "origins")
public class Origin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "origin_id") // Đảm bảo sử dụng tên đúng
    private Integer originId;

    @Column(name = "origin_name", nullable = false, columnDefinition = "NVARCHAR(255)", unique = true)
    private String originName;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime updatedAt;

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
}
