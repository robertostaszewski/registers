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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RegistersApplication implements CommandLineRunner {

	@Autowired
	private RegisterRepository registerRepository;

	@Bean
	public RegisterService getRegisterService(RegisterRepository registerRepository) {
		return new SimpleRegisterService(registerRepository);
	}

	public static void main(String[] args) {
		SpringApplication.run(RegistersApplication.class, args);
	}

	@Override
	public void run(String... args) {
		registerRepository.save(new Register(null, "drinks", 0));
		registerRepository.save(new Register(null, "car", 1000));
		registerRepository.save(new Register(null, "food", 0));
	}
}
