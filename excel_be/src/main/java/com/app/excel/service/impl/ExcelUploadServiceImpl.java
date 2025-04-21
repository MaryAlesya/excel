package com.app.excel.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.excel.model.Uploads;
import com.app.excel.model.User;
import com.app.excel.model.UserActivity;
import com.app.excel.repository.UploadsRepository;
import com.app.excel.repository.UserActivityRepository;
import com.app.excel.service.ExcelUploadService;
import com.app.excel.service.UserService;

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {

	@Autowired
	UserService userService;

	@Autowired
	UploadsRepository uploadsRepository;

	@Autowired
	UserActivityRepository userActivityRepository;

	public boolean saveUploads(String originalName, String savedFileName, String type, Long userId) {
		Uploads upload = null;
		User user = userService.findUserById(userId);
		try {
			if (user != null ) {
				/** Save Uploads **/
				upload = new Uploads();
				upload.setOriginalFileName(originalName);
				upload.setSavedFileName(savedFileName);
				upload.setType(type);
				upload.setUploadedOn(LocalDateTime.now());
				upload.setUser(user);
				/** Save User Activity log **/
				UserActivity activity = new UserActivity();
				activity.setAction("Upload Success");
				activity.setActionedOn(LocalDateTime.now());
				activity.setUser(user);
				if(savedFileName != null) {
					upload.setStatus("Success");
					
				}
				else {
					upload.setStatus("Failure");
					activity.setAction("Upload Failed");
					return false;
				}
				uploadsRepository.save(upload);
				userActivityRepository.save(activity);
			}

		} catch (Exception e) {
			upload = new Uploads();
			upload.setOriginalFileName(originalName);
			upload.setSavedFileName(savedFileName);
			upload.setType(type);
			upload.setUploadedOn(LocalDateTime.now());
			upload.setUser(user);
			upload.setStatus("Failure");
			uploadsRepository.save(upload);

			/** Save User Activity log **/
			UserActivity activity = new UserActivity();
			activity.setAction("Upload Failed");
			activity.setActionedOn(LocalDateTime.now());
			userActivityRepository.save(activity);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Uploads findFileById(Long id) {
		Optional<Uploads> file = uploadsRepository.findById(id);
		return file.get();

	}
}
