package com.codewithmithun.employee_payroll_system.service;

import com.codewithmithun.employee_payroll_system.entity.Employee;
import com.codewithmithun.employee_payroll_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeRepository employeeRepository;

    public CompletableFuture<String> generatePayrollReport(){
        List<Employee> employees = employeeRepository.findAll();
        StringBuilder sb = new StringBuilder("Payroll Report: \n");

        for(Employee e: employees){
            sb.append(e.getName())
                    .append(" -Salary: ").append(e.getSalary())
                    .append(" -Dept: ").append(e.getDepartment()).append("\n");
        }

        try{
            Thread.sleep(4000);

        }catch (InterruptedException e){
            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(sb.toString());
    }

}
