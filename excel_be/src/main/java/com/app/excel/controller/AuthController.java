package com.app.excel.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.excel.common.Crypt;
import com.app.excel.dto.AuthTokenDto;
import com.app.excel.dto.ResultDto;
import com.app.excel.dto.UserDto;
import com.app.excel.model.User;
import com.app.excel.security.JwtTokenProvider;
import com.app.excel.service.AuthService;
import com.app.excel.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private String token;

	/**
	 * @PostMapping("/login") public ResponseEntity<String> login(@RequestBody
	 * UserDto userDto) { boolean isAuthenticated =
	 * userService.authenticate(userDto); if (isAuthenticated) { return
	 * ResponseEntity.ok("Login successful"); } else { return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); }
	 * }
	 **/

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletResponse response) throws Exception {
		userDto.setPassword(Crypt.decrypt(userDto.getPassword()));
		User user = userService.findUserByUserName(userDto.getUsername());
		if (user == null) {
			//System.out.println("Entered into user empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			//System.out.println("Entered into else");
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtTokenProvider.generateToken(authentication);

			return ResponseEntity.ok(new AuthTokenDto(token, authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","))));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<ResultDto> register(@RequestBody UserDto userDto) {
		ResultDto isRegistered = authService.register(userDto);
		if (isRegistered.isSuccess()) {
			return new ResponseEntity<>(isRegistered, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(isRegistered, HttpStatus.BAD_REQUEST);
		}
	}
}