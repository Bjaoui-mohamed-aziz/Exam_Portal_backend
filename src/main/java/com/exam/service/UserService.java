package com.exam.service;

import com.exam.Model.User;
import com.exam.Model.UserRole;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserService {

    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    public User getUser(String username);

    public void deleteUser(Long userId);
}
