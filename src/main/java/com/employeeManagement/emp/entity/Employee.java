package com.employeeManagement.emp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Long id;
	private String name;
	private String email;
	
	private Department department;
	private Role role;
	
	

}
