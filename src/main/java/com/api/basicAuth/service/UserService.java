package com.api.basicAuth.service;


import java.util.List;

import com.api.basicAuth.entity.User;
import com.api.basicAuth.model.UserModel;

import jakarta.validation.Valid;

public interface UserService
{
	
	User addUser(UserModel userModel);
	
    List<User> getAllUsers();
    
    User findByEmail(String email);
}
