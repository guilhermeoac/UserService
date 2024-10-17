package com.ntd.userservice.repository.interfaces;

import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import com.ntd.userservice.repository.model.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findUserEntityByUsername(String username);

    @Query("select new com.ntd.userservice.repository.dto.UserBalanceOutputDTO(u.id, u.username, b.balance, b.id, b.version) " +
            "from UserEntity u join BalanceEntity b on b.user = u " +
            "where u.username = :username")
    Optional<UserBalanceOutputDTO> findUserBalanceInfo(@Param("username")String username);
}

