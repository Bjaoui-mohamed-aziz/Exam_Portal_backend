package com.exam;

import com.exam.Model.Role;
import com.exam.Model.User;
import com.exam.Model.UserRole;

import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class examserverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	public static void main(String[] args) {

		SpringApplication.run(examserverApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {

			System.out.println("loumour 6 zit");


/*	User user = new User();

		user.setFirstName("aziz");
		user.setLastName("bjaoui");
		user.setUsername("azizz");
		user.setPassword("abc");
		user.setEmail("azizbjaoui312@gmail.com");
		user.setProfile("photo.png");

		Role role1= new Role();
		role1.setRoleId(44L);
		role1.setRoleName("ADMIN");
		Set<UserRole> userRoleSet = new HashSet<>();
		UserRole userRole = new UserRole();
        userRole.setRole(role1);

		userRole.setUser(user);
		userRoleSet.add(userRole);

		User user1 = this.userService.createUser(user, userRoleSet);
		System.out.println(user1.getUsername());*/

		}}
