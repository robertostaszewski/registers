package com.assignment.registers.repositories;

import com.assignment.registers.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {
}
