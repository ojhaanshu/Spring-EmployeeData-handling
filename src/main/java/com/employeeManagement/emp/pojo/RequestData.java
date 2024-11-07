package com.employeeManagement.emp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class RequestData {
	private String name;
	private String email;
	private int departmentId;
	private int roleId; 

}
