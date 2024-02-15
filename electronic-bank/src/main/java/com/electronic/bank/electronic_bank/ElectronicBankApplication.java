package com.electronic.bank.electronic_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectronicBankApplication {

	public static String name = "alberto";

	public static void main(String[] args) {
		SpringApplication.run(ElectronicBankApplication.class, args);
		System.out.println("Hola "+name);
	}

} 
