package com.imaginnovate.test.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.test.employee.dto.EmployeeDto;
import com.imaginnovate.test.employee.service.EmployeeDetailsServiceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("imaginnovate/api")
public class EmployeeController {
	@Autowired
	private EmployeeDetailsServiceImp EmployeeDetailsService;
	
	
	
	Logger logger =LoggerFactory.getLogger(EmployeeController.class);

	

	
	@PostMapping("/employees")
	public ResponseEntity<?> addemploye(@Validated @RequestBody EmployeeDto employeedto, BindingResult binres) {
		if (binres.hasErrors()) {
			Map<String, String> validationsMap = new HashMap<String, String>();
			for (FieldError error : binres.getFieldErrors()) {
				validationsMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(validationsMap, HttpStatus.BAD_REQUEST);

		} else {

			EmployeeDto st = EmployeeDetailsService.AddEmployee(employeedto);
	        logger.debug("A DEBUG Message");
			return new ResponseEntity<EmployeeDto>(st, HttpStatus.CREATED);
		}
	}
	
	

	
	
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDto>> getallemployee(
			@RequestParam(value = "Pageno", defaultValue = "0", required = false) Integer pageno,
			@RequestParam(value = "pagesize", defaultValue = "4", required = false) Integer pagesize,
			@RequestParam(value = "sotrby", defaultValue = "empid", required = false) String sotrby,
			@RequestParam(value = "sotrdsc", defaultValue = "asc", required = false) String sotrdsc) {
		List<EmployeeDto> employess = EmployeeDetailsService.GetAllEmployee(pageno, pagesize, sotrby, sotrdsc);

		return new ResponseEntity<List<EmployeeDto>>(employess, HttpStatus.OK);

	}
	
	
	
	
	

	@GetMapping("/employees/{id}")
	public ResponseEntity<?>getEmployeeTaxDeductionCurrentYear(@PathVariable("id")Long id){
		
		
		return new ResponseEntity<Double>(EmployeeDetailsService.getEmployeetaxdeductionforthecurrentfinancialyear(id),HttpStatus.OK);
		
	}
	

}
