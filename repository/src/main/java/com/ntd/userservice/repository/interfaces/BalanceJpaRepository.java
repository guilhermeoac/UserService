package com.ntd.userservice.repository.interfaces;

import com.ntd.userservice.repository.model.BalanceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceJpaRepository extends JpaRepository<BalanceEntity, Long> {

    Optional<BalanceEntity> findBalanceEntityByUser_Id(Long userId);
}

