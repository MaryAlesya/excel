package com.app.excel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {

	private static final String UPLOAD_DIR = "D:/Mary/excel-uploads/";

	public String storeFile(MultipartFile file, String prefix) {
		String savedFileName = null;
		try {
			
		    String originalFilename = file.getOriginalFilename();
		    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		    String newFileName = prefix + "_" + timestamp + "_" + originalFilename;
	
		    Path targetPath = Paths.get(UPLOAD_DIR).resolve(newFileName).normalize();
		    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		    savedFileName =  targetPath.toString();
		    return savedFileName;
			}catch(Exception e) {
				return savedFileName;
			}
	}

}
