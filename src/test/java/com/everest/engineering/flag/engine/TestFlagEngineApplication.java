package com.everest.engineering.flag.engine;

import org.springframework.boot.SpringApplication;

public class TestFlagEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlagEngineApplication::main).with(TestFlagEngineApplication.class).run(args);
	}

}
