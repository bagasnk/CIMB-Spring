package com.cimb.tokolapak.service;
import java.util.Optional;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;

public interface EmployeeService {
	
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress); 
	public Optional<Employee> addEmployeeAddress(int id,  EmployeeAddress employeeAddress);
	
}
