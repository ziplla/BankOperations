package com.bankoperations.bankoperations.repository;

import com.bankoperations.bankoperations.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

    User findByUsername(String username);

    Slice<Object> findAll(Specification<User> specification, Pageable pageable);
}
