package com.example.llk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(value="com.example.llk.dao")
public class SpringbootLlk2Application{

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootLlk2Application.class);
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootLlk2Application.class, args);
	}
}
