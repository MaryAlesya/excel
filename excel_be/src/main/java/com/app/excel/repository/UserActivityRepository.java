package com.app.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.excel.model.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
	
	List<UserActivity> findByOrderByActionedOnDesc();

	UserActivity save(UserActivity activity);
	
	
}
