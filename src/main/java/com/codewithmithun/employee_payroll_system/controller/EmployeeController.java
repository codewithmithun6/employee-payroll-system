package com.codewithmithun.employee_payroll_system.controller;

import com.codewithmithun.employee_payroll_system.entity.Employee;
import com.codewithmithun.employee_payroll_system.service.EmployeeService;
import com.codewithmithun.employee_payroll_system.service.PayrollService;
import com.codewithmithun.employee_payroll_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PayrollService payrollService;
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Employee> add(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}/tax")
    public ResponseEntity<String> calculateTax(@PathVariable Long id) throws Exception {
        Employee employee = employeeService.getAll().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow();
        Future<Double> result = payrollService.calculateTax(employee);
        return ResponseEntity.ok("Tax: "+result.get());
    }

    @GetMapping("/{id}/net")
    public ResponseEntity<String> calculateNetSalary(@PathVariable Long id) throws Exception {
        Employee employee = employeeService.getAll().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow();
        Future<Double> result = payrollService.calculateNetSalary(employee);
        return ResponseEntity.ok("Net Salary: "+result.get());
    }

    @GetMapping("/report")
    public CompletableFuture<ResponseEntity<String>> generateReport(){
        return reportService.generatePayrollReport()
                .thenApply(ResponseEntity::ok);
    }


}
