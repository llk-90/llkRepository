package com.example.llk;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.example.llk")
@MapperScan("com.example.llk.dao")
public class SpringbootLlkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLlkApplication.class, args);
	}
}
