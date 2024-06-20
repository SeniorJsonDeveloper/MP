package ru.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.auth.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, String> {


    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
