package com.example.zhaowang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.zhaowang.mapper")
public class ZhaoWangApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhaoWangApplication.class, args);
	}

}
