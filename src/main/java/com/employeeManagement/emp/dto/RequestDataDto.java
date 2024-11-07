package com.employeeManagement.emp.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestDataDto {
	
		private String name;
		private String email;
		private int departmentId;
		private int roleId; 

	


}
