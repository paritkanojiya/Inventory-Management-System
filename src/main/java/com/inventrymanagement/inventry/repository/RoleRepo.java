package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.Role;
import com.inventrymanagement.inventry.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    List<Role> findByUser(User user);
}