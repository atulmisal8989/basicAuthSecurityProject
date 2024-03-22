package com.api.basicAuth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.basicAuth.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String>
{
	User findByEmail(String email);
}