package com.app.excel.service;

import com.app.excel.model.Uploads;

public interface ExcelUploadService {
	
    boolean saveUploads(String originalfileName, String savedFileName, String type, Long userId);
    
    Uploads findFileById(Long id);
}