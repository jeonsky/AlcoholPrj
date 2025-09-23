package com.sky.alcohol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @CreatedDate, @LastModifiedDate 자동 기록
public class AlcoholApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlcoholApplication.class, args);
	}
}
