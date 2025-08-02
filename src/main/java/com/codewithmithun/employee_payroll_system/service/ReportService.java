package com.codewithmithun.employee_payroll_system.service;

import com.codewithmithun.employee_payroll_system.entity.Employee;
import com.codewithmithun.employee_payroll_system.repository.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    // Report generate in PDF format (This is not in table format)
//    public byte[] generatePayrollReportPdf() throws IOException{
//        List<Employee> employees = employeeRepository.findAll();
//
//        ByteArrayOutputStream boas = new ByteArrayOutputStream();
//        PdfWriter writer = new PdfWriter(boas);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        document.add(new Paragraph("Employee Payroll Report"))
//                .setBold()
//                .setFontSize(18)
//                .setTextAlignment(TextAlignment.CENTER)
//                .setBottomMargin(20);
//
//        for(Employee e:employees){
//
//            document.add(new Paragraph("Name: "+e.getName()));
//            document.add(new Paragraph("Department: "+e.getDepartment()));
//            document.add(new Paragraph("Salary: ₹"+e.getSalary()));
//            document.add(new Paragraph("------------------------------------------"));
//        }
//        document.close();
//        return boas.toByteArray();
//
//    }

    // report generate in table format
    public byte[] generatePayrollReportPdf() throws IOException {
        List<Employee> employees = employeeRepository.findAll();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Title
        document.add(new Paragraph("Employee Payroll Report")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Define table with 3 columns
        float[] columnWidths = {200F, 150F, 100F};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Add table headers
        table.addHeaderCell(new Cell().add(new Paragraph("Name").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Department").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Salary ($)").setBold()));

        // Add rows
        for (Employee e : employees) {
            table.addCell(new Cell().add(new Paragraph(e.getName())));
            table.addCell(new Cell().add(new Paragraph(e.getDepartment())));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", e.getSalary()))));
        }

        // Add table to document
        document.add(table);
        document.close();

        return baos.toByteArray();
    }


}
