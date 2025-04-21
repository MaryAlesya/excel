package com.app.excel.strategy;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.excel.dto.ProcessedDataDto;
import com.app.excel.dto.UploadDto;

public interface ExcelProcessingStrategy {
	
	boolean supports(String type); 
	
	String process(MultipartFile file,String type) throws IOException; 
	
	List<UploadDto> getUploads(String type, Long userId);
}

