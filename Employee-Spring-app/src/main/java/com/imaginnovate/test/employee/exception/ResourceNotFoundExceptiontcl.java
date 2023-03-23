package com.imaginnovate.test.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptiontcl extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	public ResourceNotFoundExceptiontcl(String msg) {
		super(msg);
	}
}
