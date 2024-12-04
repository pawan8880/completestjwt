package com.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
	
	Optional<UserInfo> findByName(String userName);

}
