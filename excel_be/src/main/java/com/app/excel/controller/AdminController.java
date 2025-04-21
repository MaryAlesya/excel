package com.app.excel.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.excel.dto.DashboardStatsDto;
import com.app.excel.dto.ProcessedDataDto;
import com.app.excel.dto.UploadDto;
import com.app.excel.model.Uploads;
import com.app.excel.service.AdminService;
import com.app.excel.service.ExcelUploadService;



@Controller
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200") 
public class AdminController {
	
	private static final String FILE_DIRECTORY = "D:/Mary/excel-uploads/";
	
	@Autowired
	ExcelUploadService uploadService;
	
	@Autowired
	AdminService adminService;
	
	
	@GetMapping("/excel/download/{id}/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, @PathVariable String filename) throws IOException {
		Uploads file = uploadService.findFileById(id);
		Path filePath = Paths.get(file.getSavedFileName()).normalize();
	    Resource resource = new UrlResource(filePath.toUri());
	    if (!resource.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok()
	        .contentType(MediaType.APPLICATION_OCTET_STREAM)
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	        .body(resource);
	}
	
	 @GetMapping("/uploads/{type}")
	    public ResponseEntity<List<UploadDto>> getAllUsers(@PathVariable String type) {
	        List<UploadDto> allUploads = adminService.getAllUploads(type);
	        if (allUploads.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(allUploads);
	 }
	 
	 @GetMapping("/getSalesData")
	    public ResponseEntity<List<ProcessedDataDto>> getSalesData() {
	        List<ProcessedDataDto> sales = adminService.getSalesData();
	        if (sales.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(sales);
	 }
	 
	 @GetMapping("/getPurchasesData")
	    public ResponseEntity<List<ProcessedDataDto>> getPurchasesData() {
	        List<ProcessedDataDto> purchases = adminService.getPurchasesData();
	        if (purchases.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(purchases);
	 }
	 
	 @PutMapping("/saveSale/{id}")
	 public ResponseEntity<?> updateSales(@PathVariable Long id, @RequestBody ProcessedDataDto salesDto) {
		 try {
		     ProcessedDataDto dataDto = adminService.saveSalesRecord(id, salesDto);
		     return ResponseEntity.ok(dataDto);
			 } catch (NoSuchElementException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found with ID: " + id);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Failed to update sales: " + e.getMessage());
	    }
	 }
	 
	 @PutMapping("/savePurchase/{id}")
	 public ResponseEntity<?> updatePurchase(@PathVariable Long id, @RequestBody ProcessedDataDto purchaseDto) {
		 try {
		     ProcessedDataDto dataDto = adminService.savePurchaseRecord(id, purchaseDto);
		     return ResponseEntity.ok(dataDto);
			 } catch (NoSuchElementException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found with ID: " + id);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Failed to update sales: " + e.getMessage());
	    }
	 }
	 
	 @GetMapping("/dashboard")
	 public ResponseEntity<DashboardStatsDto> getDashboardStats() {
		 DashboardStatsDto dto = adminService.getDashboardMetrics();
	     return ResponseEntity.ok(dto);
	 }
	 
}
