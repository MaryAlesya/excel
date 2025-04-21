package com.app.excel.dto;

import java.util.Objects;

public class ResultDto {
	
	private boolean isSuccess;
	private String result;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	@Override
	public int hashCode() {
		return Objects.hash(isSuccess, result);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultDto other = (ResultDto) obj;
		return isSuccess == other.isSuccess && Objects.equals(result, other.result);
	}
	@Override
	public String toString() {
		return "ResultDto [isSuccess=" + isSuccess + ", result=" + result + "]";
	}
	
	
	
	
	
}
