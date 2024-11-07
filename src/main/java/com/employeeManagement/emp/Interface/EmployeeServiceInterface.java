package com.employeeManagement.emp.Interface;

import java.util.List;
import java.util.Optional;

import com.employeeManagement.emp.dto.RequestDataDto;
import com.employeeManagement.emp.entity.Employee;

public interface EmployeeServiceInterface {
	
	public int addEmployee(RequestDataDto data);
	public Employee getEmployeeById(Long id);

}
