package com.srv.sumit.cachingredisdemo;

import com.github.javafaker.Faker;
import com.srv.sumit.cachingredisdemo.model.Company;
import com.srv.sumit.cachingredisdemo.model.Employee;
import com.srv.sumit.cachingredisdemo.service.CompanyHashesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CachingRedisDemoApplication implements CommandLineRunner {
    @Autowired
    private Faker faker;
    /* @Autowired
     private ObjectHolderService service;*/
   /* @Autowired
    private AtomicLongService service;*/
   /* @Autowired
    private BitSetService service;*/
    @Autowired
    private CompanyHashesService service;

    public static void main(String[] args) {
        SpringApplication.run(CachingRedisDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            com.github.javafaker.Company fakerCompany = faker.company();

            Company company = new Company(fakerCompany.name(), fakerCompany.catchPhrase(), fakerCompany.buzzword(), fakerCompany.industry(), fakerCompany.url());
            Employee employee = new Employee(faker.random().hex(10),
                    faker.name().fullName(), faker.random().nextInt(22, 60),
                    Math.ceil(faker.random().nextDouble() * 10000),
                    company);
            /*service.storeObjectInBucket("employee", employee.getEmployeeId(), employee);*/
            employees.add(employee);
        }
        service.storeEmployee(employees, "employee");
       /* service.storeCompany(companies, "company", ids);
        service.getCompany(ids);*/
        //service.printAllEmployees();

        service.closeConnection();
        //service.performOperationsOnBits();
    }
}
