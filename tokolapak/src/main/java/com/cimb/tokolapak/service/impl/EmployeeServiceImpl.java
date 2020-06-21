package com.cimb.tokolapak.service.impl;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;

	@Override
	@Transactional
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress) {
		employeeAddress.getEmployee().setEmployeeAddress(null); // putus hubungan dari employe ke address
		employeeAddress.setEmployee(null);	// putushubungan dari addres sampai employee
		employeeAddressRepo.delete(employeeAddress);
	}
	
	@Override
	@Transactional
	public Employee addEmployeeAddress(int employeeId, EmployeeAddress employeeAddress){
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		
		if(findEmployee == null)
			throw new RuntimeException("Employee not found");
		
		findEmployee.setEmployeeAddress(employeeAddress);
		return employeeRepo.save(findEmployee);
		/*
		 * employeeAddress.setEmployee(findEmployee.get());
		 * employeeAddressRepo.save(employeeAddress);
		 * employeeAddress.getEmployee().setEmployeeAddress(employeeAddress);
		 * employeeRepo.save(findEmployee.get()); return employeeRepo.findById(id);
		 */
	}
	
}
