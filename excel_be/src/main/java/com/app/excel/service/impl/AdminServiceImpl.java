package com.app.excel.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.excel.dto.DashboardStatsDto;
import com.app.excel.dto.ProcessedDataDto;
import com.app.excel.dto.UploadDto;
import com.app.excel.dto.UserActivityDto;
import com.app.excel.dto.UserDto;
import com.app.excel.model.Purchases;
import com.app.excel.model.Sales;
import com.app.excel.model.Uploads;
import com.app.excel.model.User;
import com.app.excel.model.UserActivity;
import com.app.excel.repository.PurchasesRepository;
import com.app.excel.repository.SalesRepository;
import com.app.excel.repository.UploadsRepository;
import com.app.excel.repository.UserActivityRepository;
import com.app.excel.repository.UserRepository;
import com.app.excel.service.AdminService;
import com.app.excel.service.UserService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UploadsRepository uploadsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	SalesRepository salesRepository;

	@Autowired
	PurchasesRepository purchaseRepository;
	
	@Autowired
	UserActivityRepository userActivityRepository;

	public List<UploadDto> getAllUploads(String type) {
		String status = "Success";
		List<Uploads> uploads = uploadsRepository.findByTypeAndStatusOrderByIdDesc(type, status);
		return uploads.stream().map(this::mapUploadsToDto).collect(Collectors.toList());
	}

	public UploadDto mapUploadsToDto(Uploads uploads) {
		if (uploads == null)
			return null;

		UploadDto dto = new UploadDto();
		dto.setId(uploads.getId());
		dto.setOriginalFileName(uploads.getOriginalFileName());
		dto.setType(uploads.getType());
		dto.setUploadedOn(uploads.getUploadedOn());
		dto.setUploadedBy(userService.mapToDto(uploads.getUser()));
		return dto;
	}

	public List<ProcessedDataDto> getSalesData() {
		List<Sales> sales = salesRepository.findAll();
		return sales.stream().map(this::mapSalesToDto).collect(Collectors.toList());
	}

	public ProcessedDataDto mapSalesToDto(Sales sale) {
		if (sale == null)
			return null;

		ProcessedDataDto dto = new ProcessedDataDto();
		dto.setId(sale.getId());
		dto.setItem(sale.getItem());
		dto.setValue1(sale.getValue1());
		dto.setValue2(sale.getValue2());
		dto.setValue3(sale.getValue3());
		dto.setTotal(sale.getTotal());
		dto.setAverage(sale.getAverage());
		dto.setScore(sale.getScore());
		dto.setDate(sale.getSaleDate());
		return dto;
	}

	public List<ProcessedDataDto> getPurchasesData() {
		List<Purchases> purchases = purchaseRepository.findAll();
		return purchases.stream().map(this::mapPurchasesToDto).collect(Collectors.toList());
	}

	public ProcessedDataDto mapPurchasesToDto(Purchases purchase) {
		if (purchase == null)
			return null;
		ProcessedDataDto dto = new ProcessedDataDto();
		dto.setId(purchase.getId());
		dto.setItem(purchase.getItem());
		dto.setValue1(purchase.getValue1());
		dto.setValue2(purchase.getValue2());
		dto.setValue3(purchase.getValue3());
		dto.setTotal(purchase.getTotal());
		dto.setAverage(purchase.getAverage());
		dto.setScore(purchase.getScore());
		dto.setDate(purchase.getPurchaseDate());
		return dto;
	}

	public ProcessedDataDto saveSalesRecord(Long id,ProcessedDataDto saleDto) {
		ProcessedDataDto dataDto = null;
		try {
		Optional<Sales> sale = salesRepository.findById(saleDto.getId());
		if(sale.isPresent()) {
			sale.get().setTotal(saleDto.getTotal());
			sale.get().setAverage(saleDto.getAverage());
			sale.get().setScore(saleDto.getScore());
			salesRepository.save(sale.get());
			dataDto = this.mapSalesToDto(sale.get());
		}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return dataDto;
	}
	
	public ProcessedDataDto savePurchaseRecord(Long id,ProcessedDataDto purchaseDto) {
		ProcessedDataDto dataDto = null;
		try {
		Optional<Purchases> purchase = purchaseRepository.findById(purchaseDto.getId());
		if(purchase.isPresent()) {
			purchase.get().setTotal(purchaseDto.getTotal());
			purchase.get().setAverage(purchaseDto.getAverage());
			purchase.get().setScore(purchaseDto.getScore());
			purchaseRepository.save(purchase.get());
			dataDto = this.mapPurchasesToDto(purchase.get());
		}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return dataDto;
	}
	
	public DashboardStatsDto getDashboardMetrics() {
		 DashboardStatsDto dto = new DashboardStatsDto();
	     dto.setTotalUsers(userRepository.count());
	     dto.setTotalUploads(uploadsRepository.count());
	     dto.setSuccessfulProcesses(uploadsRepository.countByStatus("Success"));
	     dto.setFailedProcesses(uploadsRepository.countByStatus("Failure"));
	     Optional<Uploads> latestUpload = uploadsRepository.findTopByOrderByUploadedOnDesc();
	     dto.setLasUploadedTime(latestUpload.map(Uploads::getUploadedOn).orElse(null));
	     
	     List<UserActivity> userActvities = userActivityRepository.findByOrderByActionedOnDesc();
	     List<UserActivityDto> activityLogs = userActvities.stream().map(this::mapToDto).collect(Collectors.toList());
	     dto.setUserActivities(activityLogs);
	     return dto;
	    
	}
	
	
	  public UserActivityDto mapToDto(UserActivity userActivity) { 
		    UserActivityDto dto = new UserActivityDto(); 
	    	dto.setId(userActivity.getId()); 
	    	dto.setUserName(userActivity.getUser().getUserName()); 
	    	dto.setAction(userActivity.getAction());
	    	dto.setActionedOn(userActivity.getActionedOn());
	    	return dto; 
	    }
	    
	}
