package com.employeeManagement.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employeeManagement.emp.DAO.EmployeeDao;
import com.employeeManagement.emp.Interface.EmployeeServiceInterface;
import com.employeeManagement.emp.dto.RequestDataDto;
import com.employeeManagement.emp.entity.Employee;

@Service
public class EmployeeService implements EmployeeServiceInterface{
	
	private EmployeeDao dao;
	
	public EmployeeService(EmployeeDao dao) {
		this.dao = dao;
	}

	@Override
	public int addEmployee(RequestDataDto data) {
		int result = dao.addEmployee(data);
		return result;
	}

	@Override
	public Employee getEmployeeById(Long id) {
		System.out.println("Inside service of getEmployee : "+id);
		Employee e  =dao.getEmployee(id);
		System.out.println("Output returned from getEmployeedao : "+e);
		
		return e;
	}

}
