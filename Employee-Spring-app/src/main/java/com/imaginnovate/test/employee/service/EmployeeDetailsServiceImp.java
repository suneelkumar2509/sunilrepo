package com.imaginnovate.test.employee.service;

import com.imaginnovate.test.employee.dto.EmployeeDto;
import com.imaginnovate.test.employee.exception.ResourceNotFoundExceptiontcl;
import com.imaginnovate.test.employee.model.Employee;
import com.imaginnovate.test.employee.model.EmployeeTaxDetails;
import com.imaginnovate.test.employee.repositopre.EmployeeDetailsRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsServiceImp implements EmployeeDetailsService {

	@Autowired
	private EmployeeDetailsRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	
	

	@Override
	public EmployeeDto AddEmployee( EmployeeDto employeedto) {
		System.out.println(employeedto);
		Employee employee = modelMapper.map(employeedto, Employee.class);
		EmployeeDto savedemployeeDto = null;
		Employee savedemployee = null;
		try {
			savedemployee = employeeRepository.save(employee);

		} catch (Exception e) {
			throw new ResourceNotFoundExceptiontcl("employee allready existed");
		}
		savedemployeeDto = modelMapper.map(savedemployee, EmployeeDto.class);
		return savedemployeeDto;

	}

	
	
	@Override
	public List<EmployeeDto> GetAllEmployee(int pageno ,int psgesize, String sortby,String sortdrc) {
		Sort sort =sortdrc.equalsIgnoreCase(Sort.Direction.ASC.name())?
    			Sort.by(sortby).ascending():Sort.by(sortby).descending();
    	
    	Pageable pag = PageRequest.of(pageno, psgesize, sort);
  
    	Page<Employee>allemploylist =this.employeeRepository.findAll(pag);
    	List<EmployeeDto>allemploydtolist = allemploylist.stream()
    			.map((Employee)-> modelMapper
    			.map(Employee, EmployeeDto.class)).collect(Collectors.toList());
    		
		return allemploydtolist;
	}


 

	@Override
	public Double getEmployeetaxdeductionforthecurrentfinancialyear(Long id) {
		
		Double tax = 0.0;
		Double cess = 0.0;
		EmployeeTaxDetails employeetaxdetails=new EmployeeTaxDetails();
		Employee employeebyid = employeeRepository.findById(id).get();
				if(employeebyid==null) {
				throw new ResourceNotFoundExceptiontcl("EmployeeNotFoundException");
				}
				EmployeeDto employeeDto = modelMapper.map(employeebyid, EmployeeDto.class);
         LocalDate employeestartingdate = employeeDto.getDOJ();
         LocalDate employeeendingdate =LocalDate.now()  ;
         long totalemployeemonthsworking = ChronoUnit.MONTHS.between(employeestartingdate, employeeendingdate);
	    	Double totalsalaryofemployee = employeeDto.getSalary() *  totalemployeemonthsworking ;

		if (totalsalaryofemployee > 250000 && totalsalaryofemployee<=500000) 
		{

			return tax = (totalsalaryofemployee-250000)*0.05;
			
		}
		else if (totalsalaryofemployee > 500000 && totalsalaryofemployee <= 1000000) 
		{
			return tax = 12500+(totalsalaryofemployee-500000)*0.1;
		}
		else if (totalsalaryofemployee > 1000000 ) 
		{
	
			return tax =112500+(totalsalaryofemployee-1000000)*0.2 ;
		}
	
		
		if(totalsalaryofemployee>2500000) {
			return cess=(totalsalaryofemployee-2500000)*0.02;
		}
		double employeetotaltax =tax+cess;
		employeetaxdetails.setEmployeeID(employeeDto.getEmployeeID());
		employeetaxdetails.setFirstName(employeeDto.getFirstname());
		employeetaxdetails.setLastName(employeeDto.getLastname());
		employeetaxdetails.setEmail(employeeDto.getEmail());
		employeetaxdetails.setPhoneNumber(employeeDto.getPhonenumber());
		employeetaxdetails.setDOJ(employeeDto.getDOJ());
		employeetaxdetails.setSalary(employeeDto.getSalary());
		employeetaxdetails.setTotalTexAmount(tax);
		employeetaxdetails.setTotalCessAmount(cess);
		
		return employeetotaltax; 
	}


	
}
