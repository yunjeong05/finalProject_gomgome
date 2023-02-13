package com.ezen.gomgome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// secutiry 기능 개발동안 꺼둠
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class GomgomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GomgomeApplication.class, args);
	}


}
