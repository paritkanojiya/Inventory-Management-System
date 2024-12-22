package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
