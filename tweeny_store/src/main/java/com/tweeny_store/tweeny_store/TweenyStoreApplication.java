package com.tweeny_store.tweeny_store;

import com.tweeny_store.tweeny_store.model.role.Role;
import com.tweeny_store.tweeny_store.repository.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TweenyStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweenyStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository role){
		return args ->{
			if (role.findByName("USER").isEmpty()) {
				role.save(Role.builder().name("USER").build());
			}
		};
	}

}
