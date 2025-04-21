package com.app.excel.service;

import java.util.List;

import com.app.excel.dto.DashboardStatsDto;
import com.app.excel.dto.ProcessedDataDto;
import com.app.excel.dto.UploadDto;
import com.app.excel.dto.UserActivityDto;
import com.app.excel.dto.UserDto;
import com.app.excel.model.User;
import com.app.excel.model.UserActivity;

public interface AdminService {
	
	List<UploadDto> getAllUploads(String type);
	
	List<ProcessedDataDto> getSalesData();
	
	List<ProcessedDataDto> getPurchasesData();
	
	ProcessedDataDto saveSalesRecord(Long id, ProcessedDataDto record);
	
	ProcessedDataDto savePurchaseRecord(Long id, ProcessedDataDto record);
	
	DashboardStatsDto getDashboardMetrics();
	
	UserActivityDto mapToDto(UserActivity user);
	
	
	

}
