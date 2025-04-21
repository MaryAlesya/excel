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
import com.app.excel.model.Purchases;
import com.app.excel.repository.PurchasesRepository;
import com.app.excel.repository.UploadsRepository;
import com.app.excel.service.AnalystService;
import com.app.excel.util.Mathutil;

@Component
public class PurchaseDataProcessor implements ExcelProcessingStrategy {
	
	@Autowired
	private  PurchasesRepository purchaseRepository;
	
	@Autowired
	private  UploadsRepository uploadsRepository;
	
	@Autowired
	private AnalystService analystService;
	
	public PurchaseDataProcessor(PurchasesRepository pruchaseRepository, 
			UploadsRepository uploadsRepostiroy,
			AnalystService analystService) {
	    this.purchaseRepository = purchaseRepository;
	    this.uploadsRepository = uploadsRepository;
	    this.analystService = analystService;
	}

	
	@Override
	public boolean supports(String type) {
	    return "purchases".equalsIgnoreCase(type);
	}

	@Override
	public String process(MultipartFile file,String type) throws IOException {
	    return processPurchaseSheet(file, type);
	}

	private String processPurchaseSheet(MultipartFile file,String type) throws IOException {
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
		            LocalDate date = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
		            double val1 = row.getCell(1).getNumericCellValue();
		            double val2 = row.getCell(2).getNumericCellValue();
		            double val3 = row.getCell(3).getNumericCellValue();

		            double total = val1 + val2 + val3;
		            double average = total / 3;
		            String score = average > 50 ? "High" : "Low";

		            Purchases pruchase = new Purchases();
		            pruchase.setItem(item);
		            pruchase.setPurchaseDate(date);
		            pruchase.setValue1(val1);
		            pruchase.setValue2(val2);
		            pruchase.setValue3(val3);
		            pruchase.setTotal(total);
		            pruchase.setAverage(new Mathutil().roundingValue(total));
		            pruchase.setScore(score);

		            purchaseRepository.save(pruchase);
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
