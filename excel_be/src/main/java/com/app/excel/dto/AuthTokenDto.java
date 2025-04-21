package com.app.excel.dto;

public class AuthTokenDto {


	    private String token;
	    
	    private String role;

	    public AuthTokenDto(){

	    }

	    public AuthTokenDto(String token, String role){
	        this.token = token;
	        this.role = role;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return "AuthToken [token=" + token + ", role=" + role + "]";
		}

	}


