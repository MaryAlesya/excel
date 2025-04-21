package com.app.excel.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.excel.dto.UploadDto;
import com.app.excel.model.Uploads;
import com.app.excel.model.User;
import com.app.excel.repository.UploadsRepository;
import com.app.excel.service.AnalystService;
import com.app.excel.service.UserService;

@Service
public class AnalystServiceImpl implements AnalystService {
	
	@Autowired
	UploadsRepository uploadsRepository;
	
	@Autowired
	UserService userService;
	
	public List<UploadDto> getUploadsById(Long id, String type) {
		User user = userService.findUserById(id);
		List<Uploads> uploads = uploadsRepository.findByUserAndType(user, type);
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
		dto.setStatus(uploads.getStatus());
		dto.setUploadedBy(userService.mapToDto(uploads.getUser()));
		return dto;
	}

	
}
