package com.codewithmithun.employee_payroll_system.service;

import com.codewithmithun.employee_payroll_system.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class PayrollService {

   private final ExecutorService executor = Executors.newFixedThreadPool(5);

   public Future<Double> calculateTax(Employee employee){
       return executor.submit(()->{
           Thread.sleep(2000);
           return employee.getSalary() * 0.18;
       });
   }

    public Future<Double> calculateNetSalary(Employee employee){
        return executor.submit(()->{
            Thread.sleep(1500);
            return employee.getSalary() - (employee.getSalary() * 0.18);
        });
    }
}
