package com.app.excel.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class UploadDto {
	
	private Long id;
	
	private String type;
	
	private LocalDateTime uploadedOn;
	
	private String originalFileName;
	
	private String savedFileName;

	private UserDto uploadedBy;
	
	private String status;
	
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
	
	

	public UserDto getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(UserDto uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, originalFileName, savedFileName, status, type, uploadedBy, uploadedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadDto other = (UploadDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(originalFileName, other.originalFileName)
				&& Objects.equals(savedFileName, other.savedFileName) && Objects.equals(status, other.status)
				&& Objects.equals(type, other.type) && Objects.equals(uploadedBy, other.uploadedBy)
				&& Objects.equals(uploadedOn, other.uploadedOn);
	}

	@Override
	public String toString() {
		return "UploadDto [id=" + id + ", type=" + type + ", uploadedOn=" + uploadedOn + ", originalFileName="
				+ originalFileName + ", savedFileName=" + savedFileName + ", uploadedBy=" + uploadedBy + ", status="
				+ status + "]";
	}
	
	

}
