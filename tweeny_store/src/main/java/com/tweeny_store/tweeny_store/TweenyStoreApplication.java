package com.tweeny_store.tweeny_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TweenyStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweenyStoreApplication.class, args);
	}

}
