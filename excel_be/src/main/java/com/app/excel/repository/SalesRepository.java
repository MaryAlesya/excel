package com.app.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.excel.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {} 
