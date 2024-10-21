package com.exam.Repo;

import com.exam.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User ,Long> {


    public User findByUsername(String username);

    public User findUserById(Long userId);
}