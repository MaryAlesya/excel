package com.app.excel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id 
	@GeneratedValue
	private Long id;
	
	private String userName;
	
	private String password;
	
	private String role; //ADMIN, ANALYST
	
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Uploads> uploads = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<UserActivity> userActivity = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Uploads> getUploads() {
		return uploads;
	}
	public void setUploads(List<Uploads> uploads) {
		this.uploads = uploads;
	}
	public List<UserActivity> getUserActivity() {
		return userActivity;
	}
	public void setUserActivity(List<UserActivity> userActivity) {
		this.userActivity = userActivity;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, email, password, role, uploads, userActivity, userName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(uploads, other.uploads) && Objects.equals(userActivity, other.userActivity)
				&& Objects.equals(userName, other.userName);
	}
	@Override
	public String toString() {
		return "User [Id=" + id + ", userName=" + userName + ", password=" + password + ", role=" + role + ", email="
				+ email + ", uploads=" + uploads + ", userActivity=" + userActivity + "]";
	}
	
	
	
	
}
