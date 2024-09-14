package com.ntd.userservice.repository.interfaces;

import com.ntd.userservice.repository.model.BalanceEntityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceEntityHistory, Long> {

}

