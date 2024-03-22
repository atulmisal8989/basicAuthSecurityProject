package com.api.basicAuth.controller;


import com.api.basicAuth.entity.User;
import com.api.basicAuth.model.UserModel;
import com.api.basicAuth.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController 
{
	@Autowired
	private UserService service;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserModel userModel) 
	{

		if (service.findByEmail(userModel.getEmail()) != null) 
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
		}

		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

		User addedUser = this.service.addUser(userModel);

		if (addedUser != null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body("User added Sucessfully");
		} 
		else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
		}
	}


	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> users = this.service.getAllUsers();
		return ResponseEntity.ok(users);
	}


	@GetMapping("/getUserByEmail/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email) 
	{
		User user = service.findByEmail(email);

		if (user != null) 
		{
			return ResponseEntity.ok(user);
		}
		else 
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter Correct EmailId");
		}
	}
}
