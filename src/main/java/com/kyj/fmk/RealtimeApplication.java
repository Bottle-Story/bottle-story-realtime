package com.kyj.fmk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RealtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeApplication.class, args);
	}

}
