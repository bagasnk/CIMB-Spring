package com.cimb.tokolapak.service;
import java.util.Optional;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;

public interface EmployeeService {
	
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress); 
	public Employee addEmployeeAddress(int employeeId,  EmployeeAddress employeeAddress);
	
}
