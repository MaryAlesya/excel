package com.app.excel.dto;

import java.util.Objects;

public class UserDto {
	
	private Long id;
    private String username;
    private String password;
    private String email;
    private String confirmPwd;
    private String role;

    // Constructors
    public UserDto() {
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(confirmPwd, email, id, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(confirmPwd, other.confirmPwd) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", confirmPwd=" + confirmPwd + ", role=" + role + "]";
	}
	
	
    
    
}