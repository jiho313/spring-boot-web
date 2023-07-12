package kr.co.jhta.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(Exception.class)
	private String handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		return "error/unknown";
	}
	
	@ExceptionHandler(RuntimeException.class)
	private String handleRuntimeException(RuntimeException ex) {
		log.error(ex.getMessage(), ex);
		return "error/server";
	}
	
	@ExceptionHandler(DataAccessException.class)
	private String handleDataAccessException(DataAccessException ex) {
		log.error(ex.getMessage(), ex);
		return "error/db";
	}
}
