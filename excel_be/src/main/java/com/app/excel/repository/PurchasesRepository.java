package com.app.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.excel.model.Purchases;

public interface PurchasesRepository extends JpaRepository<Purchases, Long> {}
