package com.app.excel.service;

import java.util.List;

import com.app.excel.dto.UploadDto;

public interface AnalystService {
	
	List<UploadDto> getUploadsById(Long id,String type);
}
