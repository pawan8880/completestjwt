package com.jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.entity.UserInfo;
import com.jwt.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	UserInfoRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = userRepository.findByName(username);

		return userInfo.map(UserInfoDetails::new).orElseThrow(()-> 
		new UsernameNotFoundException("User not found"));
	}
	
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userRepository.save(userInfo);
		return "User Add Succesfully";
	}
	
	public List<UserInfo> getAllUser(){
		return userRepository.findAll();
	}

	
	public UserInfo getUser(Long id) {
		return userRepository.findById(id).get();
	}
}
