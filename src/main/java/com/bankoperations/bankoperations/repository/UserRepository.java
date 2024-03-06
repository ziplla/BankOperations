package com.bankoperations.bankoperations.repository;

import com.bankoperations.bankoperations.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

    User findByUsername(String username);
}
