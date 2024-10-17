package com.ntd.userservice.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_balance", schema = "userinfo")
public class BalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version()
    private Long version;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Long getVersion() {
        return version;
    }

    public BalanceEntity(Long id, BigDecimal balance, Long version, UserEntity user) {
        this.id = id;
        this.balance = balance;
        this.version = version;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BalanceEntity() {
    }
}