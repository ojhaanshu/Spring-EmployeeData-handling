package com.employeeManagement.emp.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.employeeManagement.emp.Mapper.EmployeeRowMapper;
import com.employeeManagement.emp.dto.RequestDataDto;
import com.employeeManagement.emp.entity.Employee;

@Repository
public class EmployeeDao {
	private JdbcTemplate jdbctemp;
	
	public EmployeeDao(JdbcTemplate jdbctemp) {
		this.jdbctemp = jdbctemp;
	}
	
	public int addEmployee(RequestDataDto employee) {
		String query ="Insert into employees (name,email,department_id,role_id) Values (?,?,?,?)";
		return jdbctemp.update(query,employee.getName(),employee.getEmail(),employee.getDepartmentId(),employee.getRoleId());
	}
	
	public Employee  getEmployee(Long id) {
		try {
		String query ="SELECT e.id, e.name, e.email, d.id AS department_id,  department_name, r.id AS role_id,  roles_name FROM employees e JOIN departments d ON e.department_id = d.id JOIN roles r ON e.role_id = r.id WHERE e.id = ?";
		
		//String sql = "SELECT * FROM employee WHERE id = ?";
        return jdbctemp.queryForObject(query, new Object[]{id}, new EmployeeRowMapper());
   
	}catch(Exception e) {
		System.out.println(e);
	}
		return null;
	}

}
