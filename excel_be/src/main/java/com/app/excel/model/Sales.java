package com.app.excel.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "sales") 
public class Sales { 
	@Id @GeneratedValue 
	private Long id;
	
	private String item;
	private LocalDate saleDate;
	private double value1;
	private double value2;
	private double value3;

	private double total;
	private double average;
	private String score; // High / Low
	
	// Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public LocalDate getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
	public double getValue1() {
		return value1;
	}
	public void setValue1(double value1) {
		this.value1 = value1;
	}
	public double getValue2() {
		return value2;
	}
	public void setValue2(double value2) {
		this.value2 = value2;
	}
	public double getValue3() {
		return value3;
	}
	public void setValue3(double value3) {
		this.value3 = value3;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(average, saleDate, id, item, score, total, value1, value2, value3);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sales other = (Sales) obj;
		return Double.doubleToLongBits(average) == Double.doubleToLongBits(other.average)
				&& Objects.equals(saleDate, other.saleDate) && Objects.equals(id, other.id) && Objects.equals(item, other.item)
				&& Objects.equals(score, other.score)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total)
				&& Double.doubleToLongBits(value1) == Double.doubleToLongBits(other.value1)
				&& Double.doubleToLongBits(value2) == Double.doubleToLongBits(other.value2)
				&& Double.doubleToLongBits(value3) == Double.doubleToLongBits(other.value3);
	}
	@Override
	public String toString() {
		return "Sales [id=" + id + ", item=" + item + ", date=" + saleDate + ", value1=" + value1 + ", value2=" + value2
				+ ", value3=" + value3 + ", total=" + total + ", average=" + average + ", score=" + score + "]";
	}
	

}
