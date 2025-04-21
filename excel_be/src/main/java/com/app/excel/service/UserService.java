package com.app.excel.service;

import java.util.List;
import java.util.Optional;

import com.app.excel.dto.UserDto;
import com.app.excel.model.User;


public interface UserService {
	
    User findUserByUserName(String username);
    User findUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto mapToDto(User user);
    User mapToEntity(UserDto userDto);
    UserDto saveUser(UserDto userDto);
    
}