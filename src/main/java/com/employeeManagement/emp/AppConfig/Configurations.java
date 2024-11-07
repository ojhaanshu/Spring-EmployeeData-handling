package com.employeeManagement.emp.AppConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Configurations {
	
	@Bean
	 public ModelMapper mapper () {
		return new ModelMapper();
	}
	
	public JdbcTemplate jdbc() {
		return new JdbcTemplate();
	}

}
