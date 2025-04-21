package com.app.excel.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "user_activity") 
public class UserActivity {
	
	@Id @GeneratedValue 
	private Long id;
	
	private String action;
	
	private LocalDateTime actionedOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	@JsonBackReference
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public LocalDateTime getActionedOn() {
		return actionedOn;
	}
	public void setActionedOn(LocalDateTime actionedOn) {
		this.actionedOn = actionedOn;
	}
	@Override
	public int hashCode() {
		return Objects.hash(action, actionedOn, id, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserActivity other = (UserActivity) obj;
		return Objects.equals(action, other.action) && Objects.equals(actionedOn, other.actionedOn)
				&& Objects.equals(id, other.id) && Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "UserActitivity [id=" + id + ", action=" + action + ", actionedOn=" + actionedOn + ", user=" + user
				+ "]";
	}
	
	

}
