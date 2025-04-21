package com.app.excel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.excel.dto.UserDto;
import com.app.excel.exception.ResourceNotFoundException;
import com.app.excel.model.User;
import com.app.excel.repository.UserRepository;
import com.app.excel.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final Map<String, String> userStore = new HashMap<>(); // Simulated in-memory user store

	@Autowired
	UserRepository userRepository;

	/**
	 * public Optional<User> findUserByUserName(String username) { Optional <User>
	 * user = userRepository.findByUserName(username); return user; }
	 **/

	/**
	 * public Optional<User> findUserById(Long id) { Optional <User> user =
	 * userRepository.findById(id); return user; }
	 **/
	public User findUserByUserName(String username) {
		return userRepository.findUserByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + username));
	}

	public User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::mapToDto).collect(Collectors.toList());
	}
	
	public UserDto saveUser(UserDto userDto) {
		Optional<User> user = userRepository.findById(userDto.getId());
		if(user.isPresent()) {
			user.get().setRole(userDto.getRole());
			userRepository.save(user.get());
		}
		UserDto dto = mapToDto(user.get());
		return dto;
	}

	public UserDto mapToDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setUsername(user.getUserName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
	}

	public User mapToEntity(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setUserName(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());
		return user;
	}
}
