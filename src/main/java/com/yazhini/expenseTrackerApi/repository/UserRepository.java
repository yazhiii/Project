package com.yazhini.expenseTrackerApi.repository;

import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity,Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

}
