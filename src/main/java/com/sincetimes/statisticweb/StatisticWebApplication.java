package com.sincetimes.statisticweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StatisticWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticWebApplication.class, args);
	}

}
