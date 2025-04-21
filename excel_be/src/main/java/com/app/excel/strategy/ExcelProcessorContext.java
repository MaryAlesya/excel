package com.app.excel.strategy;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.excel.dto.UploadDto;

@Component
public class ExcelProcessorContext {
	
	private final List<ExcelProcessingStrategy> strategies;

	public ExcelProcessorContext(List<ExcelProcessingStrategy> strategies) {
	    this.strategies = strategies;
	}

	public String process(MultipartFile file,String type) throws IOException {
	    return strategies.stream()
	            .filter(strategy -> strategy.supports(type))
	            .findFirst()
	            .orElseThrow(() -> new IllegalArgumentException("No strategy found for sheet: " + type))
	            .process(file,type );
	}

	public List<UploadDto> getUploads(String type, Long id) {
	    return strategies.stream()
	            .filter(strategy -> strategy.supports(type))
	            .findFirst()
	            .orElseThrow(() -> new IllegalArgumentException("No strategy found for sheet: " + type))
	            .getUploads(type, id );
	}
}
