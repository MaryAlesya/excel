package com.app.excel.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.excel.model.Uploads;
import com.app.excel.model.User;

public interface UploadsRepository  extends JpaRepository<Uploads, Long> {
	
	List<Uploads> findByUserAndType(User user, String type);
	
	List<Uploads> findByType(String type);

	long countByStatus(String string);

	Optional<Uploads> findTopByOrderByUploadedOnDesc();

	List<Uploads> findByTypeAndStatusOrderByIdDesc(String type, String status);
} 

