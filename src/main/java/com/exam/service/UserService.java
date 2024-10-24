package com.exam.service;

import com.exam.Model.User;
import com.exam.Model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {

    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    public User getUser(String username);

    public User getUserId(Long userId);

    public void deleteUser(Long userId);

    List<User> searchUsersByUsername(String username);
}
