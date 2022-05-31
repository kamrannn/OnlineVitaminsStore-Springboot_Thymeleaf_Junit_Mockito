package com.app.onlinevitaminstore.repository;

import com.app.onlinevitaminstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM users u WHERE u.username = ?1",
            nativeQuery = true)
    Optional<User> findUserByUsername(String username);

    @Query(
            value = "SELECT * FROM users u WHERE u.email = ?1",
            nativeQuery = true)
    Optional<User> findUserByEmail(String email);
}
