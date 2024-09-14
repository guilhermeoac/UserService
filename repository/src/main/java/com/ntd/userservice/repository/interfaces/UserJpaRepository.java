package com.ntd.userservice.repository.interfaces;

import com.ntd.userservice.repository.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findUserEntityByUsername(String username);
}

