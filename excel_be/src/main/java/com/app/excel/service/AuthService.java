package com.app.excel.service;

import com.app.excel.dto.ResultDto;
import com.app.excel.dto.UserDto;

public interface AuthService {
	
	boolean authenticate(UserDto userDto);
    ResultDto register(UserDto userDto);

}
