package com.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.example")
@MapperScan("com.example.dao")
public class SpringbootRuningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRuningApplication.class, args);
	}
}
