package com.efr.order.manager.app.repository;

import com.efr.order.manager.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPhoneNumber(String phoneNumber);
    List<User> findByIdGreaterThan(Long id);
}
