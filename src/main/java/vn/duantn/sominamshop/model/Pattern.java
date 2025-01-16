package vn.duantn.sominamshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.io.Serializable;

@Entity
@Table(name = "patterns")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pattern extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "pattern_name", columnDefinition = "NVARCHAR(255)")
  private String patternName;


  @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 0")
  private Integer status;

  public void setStatus(Integer status) {
    this.status = (status == null) ? 0 : status;
  }

}
