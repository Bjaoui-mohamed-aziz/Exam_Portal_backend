package com.exam.service.impl;

import com.exam.Model.User;
import com.exam.Model.UserRole;
import com.exam.Repo.RoleRepository;
import com.exam.Repo.UserRepository;
import com.exam.helper.UserFoundException;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if (local != null)
        {
            System.out.println("User is already there !!");
            throw new UserFoundException();
        } else {
             for(UserRole ur:userRoles){
                 roleRepository.save(ur.getRole());
             }
               user.getUserRoles().addAll(userRoles);
               local = this.userRepository.save(user);
           }
        return local;
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User getUserId(Long userId) {
        return this.userRepository.findUserById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public List<User> searchUsersByUsername(String username) {
        return userRepository.searchByUsername(username);  // Use the custom query method
    }
}