package com.srv.sumit.cachingredisdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String employeeId;
    private String employeeName;
    private int employeeAge;
    private double salary;
    private Company company;
}
