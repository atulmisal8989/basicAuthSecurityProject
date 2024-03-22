package com.api.basicAuth.model;


import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class UserModel
{
	private Long id;

	@NotBlank(message = "Username will Not Blank")
	@NotEmpty(message = "Username will Not Empty")
	private String username;

	@NotBlank(message = "password will Not Blank")
	@NotEmpty(message = "password will Not Empty")
    private String password;

    @Email(message = "Proper EmailId format is required")
    private String email;

    @NotBlank(message = "User role will Not Blank")
    @NotEmpty(message = "User role will Not Empty")
    private String role;
}
