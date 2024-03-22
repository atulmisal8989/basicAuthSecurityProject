package com.api.basicAuth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HomeController 
{

	   @GetMapping("/home")
	   public ResponseEntity<String> home()
	   {
		   return ResponseEntity.ok("This is Home Page");
	   }
	   
	   @GetMapping("/login")
	   public ResponseEntity<String> login()
	   {
		   return ResponseEntity.ok("This is Login Page");
	   }
	   
	   @GetMapping("/register")
	   public ResponseEntity<String> register()
	   {
		   return ResponseEntity.ok("This is Register Page");
	   }
}
