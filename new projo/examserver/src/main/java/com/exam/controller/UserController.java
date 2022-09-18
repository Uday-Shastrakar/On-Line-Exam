package com.exam.controller;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test")
	public String test() {
		return "Welcome to Backend api of Examportal";
	}
	
	
	//creating user
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
	
	user.setProfile("default.png");
	//encoding password with BcryptPasswordEncoder
	user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
	
	Set<UserRole> roles=new HashSet<>();
	Role role=new Role();
	role.setRoleId(45L);
	role.setRoleName("NORMAL");
	
	UserRole userRole=new UserRole();
	userRole.setUser(user);
	userRole.setRole(role);
	
	roles.add(userRole);
		
	return this.userService.createUser(user, roles);
	}
	
	//get user
	@GetMapping("/{username}")
	public User getuser(@PathVariable("username")String username) {
		return this.userService.getUser(username);
	}
	
	//delete the user by id
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId")Long userId) {
		this.userService.deleteUser(userId);
	}
  //put mapping
	@PutMapping("/")
	  public ResponseEntity<String> updateUser(@RequestBody User user) {  
	    try {
	      userService.updateUser(user);
	      return new ResponseEntity<String>(HttpStatus.OK);
	    }catch(NoSuchElementException ex){
	      // log the error message
	      System.out.println(ex.getMessage());
	      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	    }
	  }
	//watch video no 18
}
