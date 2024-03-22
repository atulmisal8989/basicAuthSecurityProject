package com.api.basicAuth.serviceImpl;


import com.api.basicAuth.dao.UserDao;
import com.api.basicAuth.entity.User;
import com.api.basicAuth.model.UserModel;
import com.api.basicAuth.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao dao;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public User addUser(UserModel userModel)
	{
		User user = modelMapper.map(userModel, User.class);
		
		return this.dao.save(user);
	}

	@Override
	public List<User> getAllUsers()
	{
		
		return this.dao.findAll();
	}

	
	@Override
	public User findByEmail(String email) 
	{
		return this.dao.findByEmail(email);
	}

}
