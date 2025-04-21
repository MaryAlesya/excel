package com.app.excel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.excel.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional <User> findByUserName(String userName);
	Optional <User> findByEmail(String email);
	Optional <User> findByUserNameIgnoreCase(String userName);
	Optional<User> findUserByUserName(String username);
}
