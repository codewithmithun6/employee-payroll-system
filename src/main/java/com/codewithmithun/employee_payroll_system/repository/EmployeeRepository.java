package com.codewithmithun.employee_payroll_system.repository;

import com.codewithmithun.employee_payroll_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
