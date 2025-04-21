package com.app.excel.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.excel.dto.ProcessedDataDto;
import com.app.excel.dto.UserDto;
import com.app.excel.service.UserService;



@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend
public class UserController {
	
	@Autowired
	UserService userService;
	
	 @GetMapping("/users")
	    public ResponseEntity<List<UserDto>> getAllUsers() {
	        List<UserDto> allUsers = userService.getAllUsers();
	        if (allUsers.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(allUsers);
	 }
	
		 @PutMapping("/saveUser/{id}")
		 public ResponseEntity<?> saveUser(@PathVariable Long id, @RequestBody UserDto userDto) {
			 try {
			     UserDto dto = userService.saveUser(userDto);
			     return ResponseEntity.ok(dto);
				 } catch (NoSuchElementException e) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found with ID: " + id);
			    } catch (Exception e) {
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			                .body("Failed to update User: " + e.getMessage());
		    }
		 }
	 
}