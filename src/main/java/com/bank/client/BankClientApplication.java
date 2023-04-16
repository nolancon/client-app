package com.bank.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankClientApplication.class, args);
	}
}
