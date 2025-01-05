package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.common.StartupConstant;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// DBクレデンシャルファイルを引数として渡す
		StartupConstant.DB_PROP_FILE = args[1];

		SpringApplication.run(Application.class, args);
	}

}
