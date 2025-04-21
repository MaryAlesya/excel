package com.app.excel.model;

import java.time.LocalDate;
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
@Table(name = "uploads") 
public class Uploads {
	
	@Id @GeneratedValue 
	private Long id;
	
	private String type;
	
	private LocalDateTime uploadedOn;
	
	private String originalFileName;
	
	private String savedFileName;
	
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // this column will be created in 'uploads' table
	@JsonBackReference
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDateTime getUploadedOn() {
		return uploadedOn;
	}
	public void setUploadedOn(LocalDateTime uploadedOn) {
		this.uploadedOn = uploadedOn;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getSavedFileName() {
		return savedFileName;
	}
	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, originalFileName, savedFileName, status, type, uploadedOn, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Uploads other = (Uploads) obj;
		return Objects.equals(id, other.id) && Objects.equals(originalFileName, other.originalFileName)
				&& Objects.equals(savedFileName, other.savedFileName) && Objects.equals(status, other.status)
				&& Objects.equals(type, other.type) && Objects.equals(uploadedOn, other.uploadedOn)
				&& Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "Uploads [id=" + id + ", type=" + type + ", uploadedOn=" + uploadedOn + ", originalFileName="
				+ originalFileName + ", savedFileName=" + savedFileName + ", status=" + status + ", user=" + user + "]";
	}
	
	
	
}
