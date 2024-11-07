package com.employeeManagement.emp.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.employeeManagement.emp.entity.Department;
import com.employeeManagement.emp.entity.Employee;
import com.employeeManagement.emp.entity.Role;

public class EmployeeRowMapper implements RowMapper<Employee>{

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		
 System.out.println("Setting Employee value");
		Employee employee = new Employee();
		employee.setId(rs.getLong("id"));
		employee.setName(rs.getString("name"));
		employee.setEmail(rs.getString("email"));
		
		Department department = new Department();
		department.setId(rs.getLong("department_id"));
		department.setName(rs.getString("department_name"));
		employee.setDepartment(department);
		
		Role role = new Role();
		role.setId(rs.getLong("role_id"));
		role.setName(rs.getString("roles_name"));
	    employee.setRole(role);
	System.out.println("Finished setting value for employee");	
	    return employee;
	}
	

}
