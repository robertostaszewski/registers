package com.assignment.registers;

import com.assignment.registers.entities.Register;
import com.assignment.registers.repositories.RegisterRepository;
import com.assignment.registers.services.RegisterService;
import com.assignment.registers.services.SimpleRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RegistersApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RegistersApplication.class, args);
	}

	@Autowired
	private RegisterRepository registerRepository;

	@Override
	public void run(String... args) {
		if (registerRepository.findAll().isEmpty()) {
			registerRepository.save(new Register(null, "Wallet", 1000));
			registerRepository.save(new Register(null, "Savings", 5000));
			registerRepository.save(new Register(null, "Insurance policy", 0));
			registerRepository.save(new Register(null, "Food expenses", 0));
		}
	}

	@Bean
	public RegisterService getRegisterService(RegisterRepository registerRepository) {
		return new SimpleRegisterService(registerRepository);
	}
}
