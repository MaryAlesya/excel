package com.app.excel.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class DashboardStatsDto {
	
	private long totalUsers;
	
	private long totalUploads;
	
	private long successfulProcesses;
	
	private long failedProcesses;
	
	private LocalDateTime lastUploadedTime;
	
	private List<UserActivityDto> userActivities ;
	
	public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	public long getTotalUploads() {
		return totalUploads;
	}
	public void setTotalUploads(long totalUploads) {
		this.totalUploads = totalUploads;
	}
	public long getSuccessfulProcesses() {
		return successfulProcesses;
	}
	public void setSuccessfulProcesses(long successfulProcesses) {
		this.successfulProcesses = successfulProcesses;
	}
	public long getFailedProcesses() {
		return failedProcesses;
	}
	public void setFailedProcesses(long failedProcesses) {
		this.failedProcesses = failedProcesses;
	}
	public LocalDateTime getLastUploadedTime() {
		return lastUploadedTime;
	}
	public void setLasUploadedTime(LocalDateTime lastUploadedTime) {
		this.lastUploadedTime = lastUploadedTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(failedProcesses, lastUploadedTime, successfulProcesses, totalUploads, totalUsers,
				userActivities);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DashboardStatsDto other = (DashboardStatsDto) obj;
		return failedProcesses == other.failedProcesses && Objects.equals(lastUploadedTime, other.lastUploadedTime)
				&& successfulProcesses == other.successfulProcesses && totalUploads == other.totalUploads
				&& totalUsers == other.totalUsers && Objects.equals(userActivities, other.userActivities);
	}
	@Override
	public String toString() {
		return "DashboardStatsDto [totalUsers=" + totalUsers + ", totalUploads=" + totalUploads
				+ ", successfulProcesses=" + successfulProcesses + ", failedProcesses=" + failedProcesses
				+ ", lastUploadedTime=" + lastUploadedTime + ", userActivities=" + userActivities + "]";
	}
	public List<UserActivityDto> getUserActivities() {
		return userActivities;
	}
	public void setUserActivities(List<UserActivityDto> userActivities) {
		this.userActivities = userActivities;
	}
	public void setLastUploadedTime(LocalDateTime lastUploadedTime) {
		this.lastUploadedTime = lastUploadedTime;
	}
	
	

}
