package com.app.excel.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.excel.dto.ResultDto;
import com.app.excel.dto.UserDto;
import com.app.excel.model.User;
import com.app.excel.repository.UserRepository;
import com.app.excel.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final Map<String, String> userStore = new HashMap<>(); // Simulated in-memory user store
    
	@Autowired
	UserRepository userRepository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(UserDto userDto) {
        String storedPassword = userStore.get(userDto.getUsername());
        return storedPassword != null && storedPassword.equals(userDto.getPassword());
    }

    @Override
    public ResultDto register(UserDto userDto) {
    	ResultDto result = new ResultDto();
    	try {
    		
	    	Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
	    	if(existingUser.isPresent()) {
	    		//System.out.println("User already exists:");
	    		result.setResult("Exists");
	    		result.setSuccess(true);
	    		return result;
	    	}
	    	else {
		    	if(userDto != null) {
		    		User user = new User();
		    		user.setUserName(userDto.getUsername());
		    		user.setUserName(userDto.getUsername());
		            user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode password
		    		user.setEmail(userDto.getEmail());
		    		userRepository.save(user);
		    		result.setResult("Success");
		    		result.setSuccess(true);
		    		
		    	}
	    	}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		result.setResult("Failed");
	    		result.setSuccess(false);
	    		return result;
	    	}
    		return result;
    }
    
   
}