package com.quick.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableAsync
public class QuickSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickSmsApplication.class, args);
		log.info("QuickSMSApplication started..");
	}

}




