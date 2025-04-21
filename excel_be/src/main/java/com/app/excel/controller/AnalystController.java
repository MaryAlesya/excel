package com.app.excel.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.excel.dto.UploadDto;
import com.app.excel.exception.FileProcessingException;
import com.app.excel.service.ExcelUploadService;
import com.app.excel.service.FileStorageService;
import com.app.excel.strategy.ExcelProcessorContext;

@RestController 
@RequestMapping("/api/analyst")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend
public class AnalystController {
	
	@Autowired
	private ExcelProcessorContext processorContext;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private ExcelUploadService excelUploadService;

	public AnalystController(ExcelProcessorContext processorContext, 
			FileStorageService fileStorageService,
			ExcelUploadService excelUploadService) {
	    this.processorContext = processorContext;
	    this.fileStorageService = fileStorageService;
	    this.excelUploadService = excelUploadService;
	}

	@PostMapping("/excel/upload")
	public ResponseEntity<?> upload(
	    @RequestParam("file") MultipartFile file,
	    @RequestParam("sheet") String type,
	    @RequestParam("userId") Long userId){
		try {
			if (file.isEmpty()) { 
				throw new FileProcessingException("Uploaded file is empty"); 
			}else {
			String originalFileName = file.getOriginalFilename();
			String savedFileName = fileStorageService.storeFile(file, type);
			boolean isSaved = excelUploadService.saveUploads(originalFileName, savedFileName, type, userId);
			if(savedFileName != null && isSaved) {
				String result = processorContext.process(file,type);
			    return ResponseEntity.ok(Map.of(
			            "status", result,
			            "message", "File uploaded and processed successfully.",
			            "savedFileName", savedFileName
			        ));
			}
			else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
			            "status", "error",
			            "message", "Failed to process the file: "
			        ));
				}
			}
		}catch(IOException e) {
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
			            "status", "error",
			            "message", "Failed to process file: " + e.getMessage()
			        ));
		}catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
	                "status", "error",
	                "message", ex.getMessage()
	            ));
	        }
	}

	@GetMapping("/getUploads/{id}/{type}")
	public ResponseEntity<List<UploadDto>> getUploads(
		@PathVariable Long id,
		@PathVariable String type){
		List<UploadDto> uploads = processorContext.getUploads(type,id);
			 if (uploads.isEmpty()) {
		            return ResponseEntity.noContent().build();
		        }
		        return ResponseEntity.ok(uploads);
		
	}
}
