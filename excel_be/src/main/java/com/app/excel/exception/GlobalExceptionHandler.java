package com.app.excel.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.app.excel.util.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	// Handle custom ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(
	        ResourceNotFoundException ex, WebRequest request) {

	    ErrorDetails errorDetails = new ErrorDetails(
	            HttpStatus.NOT_FOUND.value(),
	            ex.getMessage(),
	            request.getDescription(false)
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// Handle custom FileProcessingException
	@ExceptionHandler(FileProcessingException.class)
	public ResponseEntity<?> handleFileProcessingException(
	        FileProcessingException ex, WebRequest request) {

	    ErrorDetails errorDetails = new ErrorDetails(
	            HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage(),
	            request.getDescription(false)
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// Handle generic Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "An unexpected error occurred",
	            request.getDescription(false)
	    );
	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
