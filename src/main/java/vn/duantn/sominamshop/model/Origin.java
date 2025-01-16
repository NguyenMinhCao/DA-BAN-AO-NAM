package vn.duantn.sominamshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer status;


    public void setStatus(Integer status) {
        this.status = (status == null) ? 0 : status;
    }
}
