package com.imaginnovate.test.employee.service;

import java.util.List;

import com.imaginnovate.test.employee.dto.EmployeeDto;

public interface EmployeeDetailsService {
	
	public EmployeeDto AddEmployee(EmployeeDto dto);

	public 	List<EmployeeDto> GetAllEmployee(int pageno, int psgesize, String sortby, String sortdrc);
	

	Double getEmployeetaxdeductionforthecurrentfinancialyear(Long id);
}
