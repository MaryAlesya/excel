package com.app.excel.strategy;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.excel.dto.UploadDto;
import com.app.excel.model.Sales;
import com.app.excel.repository.SalesRepository;
import com.app.excel.repository.UploadsRepository;
import com.app.excel.service.AnalystService;
import com.app.excel.service.UserService;
import com.app.excel.util.Mathutil;

@Component 
public class SalesDataProcessor implements ExcelProcessingStrategy { 
	
	@Autowired
	private  SalesRepository salesRepository;
	@Autowired
	private UploadsRepository uploadsRepository;
	@Autowired
	private UserService userService;
	
	@Autowired
	private AnalystService analystService;
	
	public SalesDataProcessor(SalesRepository salesRepository, 
			UploadsRepository uploadsRepository,
			UserService userService, AnalystService analystService) {
	    this.salesRepository = salesRepository;
	    this.uploadsRepository = uploadsRepository;
	    this.userService = userService;
	    this.analystService = analystService;
	}

	@Override
	public boolean supports(String type) {
	    return "sales".equalsIgnoreCase(type);
	}
	
	@Override
	public String process(MultipartFile file,String type) throws IOException {
	    // Logic to read Excel and calculate sales-related fields
	    return processSalesSheet(file, type);
	}

	private String processSalesSheet(MultipartFile file,String type) throws IOException {
	    // Parse with Apache POI or EasyExcel, then add calculations like Total, Average, Score
	    // Return a list of DTOs
		 try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
		        Sheet sheet = workbook.getSheetAt(0);
		        Iterator<Row> rowIterator = sheet.iterator();

		        // Skip header
		        if (rowIterator.hasNext()) rowIterator.next();

		        while (rowIterator.hasNext()) {
		            Row row = rowIterator.next();

		            String item = row.getCell(0).getStringCellValue();
		            double val1 = row.getCell(1).getNumericCellValue();
		            double val2 = row.getCell(2).getNumericCellValue();
		            double val3 = row.getCell(3).getNumericCellValue();
		            LocalDate date = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();

		            double total = val1 + val2 + val3;
		            double average = total / 3;
		            String score = average > 50 ? "High" : "Low";

		            Sales sales = new Sales();
		            sales.setItem(item);
		            sales.setSaleDate(date);
		            sales.setValue1(val1);
		            sales.setValue2(val2);
		            sales.setValue3(val3);
		            sales.setTotal(total);
		            sales.setAverage(new Mathutil().roundingValue(total));
		            sales.setScore(score);

		            salesRepository.save(sales);
		        }
		    }
		 	
		 	return "Success";
	 	}

	@Override
	public List<UploadDto> getUploads(String type, Long id) {
		List<UploadDto> uploads = analystService.getUploadsById(id, type);
	    return uploads;
	}
	}

