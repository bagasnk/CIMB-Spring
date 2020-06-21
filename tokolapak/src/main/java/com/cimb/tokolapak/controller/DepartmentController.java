package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired 
	DepartmentRepo departmentRepo;
	
	@Autowired 
	EmployeeRepo employeeRepo;
	
	@GetMapping
	public Iterable<Department> getDepartments() {
		return departmentService.getAllDepartments();
	}
	
	@PostMapping
	public Department addDepartment(@RequestBody Department department) {
		return departmentRepo.save(department);
	}
	
	@DeleteMapping("/{departmentId}")
	public void deleteDepartment(@PathVariable int departmentId) {
		Department findDepartment = departmentRepo.findById(departmentId).get();
		
		findDepartment.getEmployees().forEach(employee -> {
			employee.setDepartment(null);
			employeeRepo.save(employee);
			
		});
	}
	
	/*@DeleteMapping("/{departmentId")
	public void deleteDepartment(@PathVariable int departmentId) {
		Department findDepartment = employeeRepo.findByIdDepartment(departmentId).get();
		
		
		findDepartment.setEmployees(null);
		departmentRepo.deleteById(departmentId);
*/	
}
