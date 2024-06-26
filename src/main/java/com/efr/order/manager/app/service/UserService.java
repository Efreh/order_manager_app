package com.efr.order.manager.app.service;

import com.efr.order.manager.app.entity.Role;
import com.efr.order.manager.app.entity.User;
import com.efr.order.manager.app.repository.RoleRepository;
import com.efr.order.manager.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUser() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDb = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (userFromDb != null) {
            return false;
        }
        user.setRoles(Collections.singleton((new Role(1L, "EMPLOYEE_USER"))));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return userRepository.findByIdGreaterThan(idMin);
    }
}
