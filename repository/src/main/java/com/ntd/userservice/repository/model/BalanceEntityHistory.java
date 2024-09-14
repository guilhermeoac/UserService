package com.ntd.userservice.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_balance_history", schema = "userinfo")
public class BalanceEntityHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_balance", nullable = false)
    private BigDecimal oldBalance;
    @Column(name = "new_balance", nullable = false)
    private BigDecimal newBalance;

    @ManyToOne(targetEntity = BalanceEntity.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "balance_id", nullable = false)
    private BalanceEntity balanceEntity;

    @Column(nullable = false, name = "creation_date")
    private LocalDateTime date;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }

    public BalanceEntityHistory(Long id, BigDecimal oldBalance, BigDecimal newBalance, BalanceEntity balanceEntity, LocalDateTime date) {
        this.id = id;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.balanceEntity = balanceEntity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public BalanceEntity getBalance() {
        return balanceEntity;
    }

    public void setBalance(BalanceEntity balanceEntity) {
        this.balanceEntity = balanceEntity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}