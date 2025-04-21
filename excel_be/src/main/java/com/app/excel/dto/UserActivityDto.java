package com.app.excel.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserActivityDto {
	
	private long id;
	
	private String action;
	
	private LocalDateTime actionedOn;
	
	private UserDto userDto;
	
	private String userName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(action, actionedOn, id, userDto, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserActivityDto other = (UserActivityDto) obj;
		return Objects.equals(action, other.action) && Objects.equals(actionedOn, other.actionedOn) && id == other.id
				&& Objects.equals(userDto, other.userDto) && Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "UserActivityDto [id=" + id + ", action=" + action + ", actionedOn=" + actionedOn + ", userDto="
				+ userDto + ", userName=" + userName + "]";
	}
	
	

}
