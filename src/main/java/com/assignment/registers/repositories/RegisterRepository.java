package com.assignment.registers.repositories;

import com.assignment.registers.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Extension of {@link JpaRepository} for {@link Register} class.
 */
public interface RegisterRepository extends JpaRepository<Register, Long> {
}
