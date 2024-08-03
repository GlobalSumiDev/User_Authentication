package com.GlobalSumi.Internal.Project.Registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GlobalSumi.Internal.Project.Registration.model.RegisterEmployee;

@Repository
public interface EmployeeRepository extends JpaRepository<RegisterEmployee, Long> {

	Optional<RegisterEmployee> findByEmail(String email);

	boolean existsByEmail(String email);

}
