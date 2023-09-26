package com.aec.store.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 165749843L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    private Boolean softDelete = Boolean.FALSE;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void increaseStock(int quantityToAdd) {
        if (quantityToAdd >= 0) {
            this.quantityInStock += quantityToAdd;
        }
    }

    public boolean decreaseStock(int quantityToDeduct) {
        if (quantityToDeduct > 0 && this.quantityInStock >= quantityToDeduct) {
            this.quantityInStock -= quantityToDeduct;
            return true; // The decrease was successful
        }
        return false; // Not enough stock for the decrease
    }

    @Override
    public String toString() {
        return name;
    }

}
