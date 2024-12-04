package com.jwt.controller;

import java.util.List;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.AuthRequest;
import com.jwt.entity.UserInfo;
import com.jwt.service.JwtService;
import com.jwt.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@GetMapping("/welcome")
	public String msg() {
		return "Hello moto";
	}

	@PostMapping("/adduser")
	public String addData(@RequestBody UserInfo userInfo) {
		return userInfoService.addUser(userInfo);
	}
	
	@PostMapping("/login")
	public String addUser(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if(authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUserName());
		}
		else {
			throw new UsernameNotFoundException("Invalid");
		}
	}

	@GetMapping("/getUsers")
	public List<UserInfo> getAllUsers(){
		return userInfoService.getAllUser();
	}
	
	@GetMapping("/getUser/{id}")
	public UserInfo getAllUsers(@PathVariable Long id) {
		return userInfoService.getUser(id);
	}
}
