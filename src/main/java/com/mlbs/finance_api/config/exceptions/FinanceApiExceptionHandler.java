package com.mlbs.finance_api.config.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mlbs.finance_api.entities.dto.ApiErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class FinanceApiExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorDTO> handleError404(ResourceNotFoundException ex, HttpServletRequest request) {
		return buildResponse(ErrorType.RESOURCE_NOT_FOUND, "Resource Not Found", HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorDTO> handleError400(BadRequestException ex, HttpServletRequest request) {
		return buildResponse(ErrorType.BAD_REQUEST, "Bad Request", HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ApiErrorDTO> handleError500(Throwable th, HttpServletRequest request) {
		return buildResponse(ErrorType.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, th.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorDTO> handleValidationExceptions(
	        MethodArgumentNotValidException ex, HttpServletRequest request) {

		String details = ex.getBindingResult().getFieldErrors().stream()
			    .map(err -> err.getField() + ": " + err.getDefaultMessage())
			    .collect(Collectors.joining("; "));

	    return buildResponse(
	            ErrorType.BAD_REQUEST,
	            "Validation Failed",
	            HttpStatus.BAD_REQUEST,	
	            details,
	            request.getRequestURI()
	    );
	}

	private ResponseEntity<ApiErrorDTO> buildResponse(ErrorType type, String title, HttpStatus status, String detail,
			String path) {
		ApiErrorDTO error = new ApiErrorDTO(type, title, status.value(), detail, path);
		return ResponseEntity.status(status).body(error);
	}

}
