package com.employeeManagement.emp.Controller;



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeeManagement.emp.dto.RequestDataDto;
import com.employeeManagement.emp.entity.Employee;
import com.employeeManagement.emp.pojo.RequestData;
import com.employeeManagement.emp.pojo.Users;
import com.employeeManagement.emp.service.EmployeeService;
import com.employeeManagement.emp.service.JWTTokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/employees")
public class EmployeeController {
	final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	public ModelMapper mapper;
	public EmployeeService service;
	public JWTTokenService createToken;
	public EmployeeController(ModelMapper mapper,EmployeeService service,JWTTokenService createToken) {
		this.mapper = mapper;
		this.service=service;
		this.createToken =createToken;
	}
	
	@PostMapping
	public ResponseEntity<String> addEmployeeCall(@RequestBody RequestData data) {
		logger.info("Request Data received"+data);
		RequestDataDto dataAsDto = mapper.map(data, RequestDataDto.class);
		logger.info("Data Converted to Dto"+ dataAsDto);
		
		int result = service.addEmployee(dataAsDto);
		if(result == 1) {
		return new ResponseEntity<>("Employee added",HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Access Token Expired or Invalid",HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		System.out.println("user info got :"+user);
		String Token = createToken.verify(user);
		return Token;
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeCall(@PathVariable Long id){
		logger.info("id received...now calling getEmployee");
		Employee response = service.getEmployeeById(id);
		if(response == null) {
			return new ResponseEntity(response,HttpStatus.BAD_REQUEST);	
		}
		System.out.println("Response received : "+response);
		return new ResponseEntity(response,HttpStatus.OK);
		
	}

}
