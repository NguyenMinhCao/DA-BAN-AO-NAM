package vn.duantn.sominamshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description", columnDefinition = "NVARCHAR(1500)")
    private String description;

    @Column(name = "performed_by", columnDefinition = "NVARCHAR(255)")
    private String performedBy;

    @Column(name = "note", columnDefinition = "NVARCHAR(1500)")
    private String note;

    @Column(name = "reason_return", columnDefinition = "NVARCHAR(1500)")
    private String reasonReturn;

    @Column(name = "action_time")
    private LocalDateTime actionTime;

    @PrePersist
    public void handleBeforeCreate() {
        this.actionTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
