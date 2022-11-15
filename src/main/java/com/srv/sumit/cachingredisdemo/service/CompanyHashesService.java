package com.srv.sumit.cachingredisdemo.service;

import com.srv.sumit.cachingredisdemo.model.Company;
import com.srv.sumit.cachingredisdemo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CompanyHashesService {
    private static final String DELIMITER = "#";
    @Autowired
    private RedissonClient client;

    public void storeEmployee(List<Employee> employees, String cachePrefix) {
        try {
            RBatch batch = client.createBatch();
            for (Employee employee : employees) {
                RMapAsync<Object, Object> employeeMap = batch.getMap(
                        cachePrefix + DELIMITER + employee.getEmployeeId(),
                        JsonJacksonCodec.INSTANCE);
                Map<String, Object> employeeDataMap = new HashMap<>();

                employeeDataMap.put("employeeId", employee.getEmployeeId());
                employeeDataMap.put("employeeName", employee.getEmployeeName());
                employeeDataMap.put("employeeAge", employee.getEmployeeAge());
                employeeDataMap.put("salary", employee.getSalary());
                employeeDataMap.put("company", employee.getCompany());

                employeeMap.putAllAsync(employeeDataMap);
            }
            BatchResult<?> execute = batch.execute();
            System.out.println(execute.getResponses());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void storeCompany(List<Company> companies, String cacheKeyPrefix, List<String> ids) throws InterruptedException {
        RBatch batch = client.createBatch();
        for (int i = 0; i < companies.size(); i++) {

            RMapAsync<Object, Object> map = batch.getMap(cacheKeyPrefix + DELIMITER + ids.get(i));
            map.fastPutAsync("companyName", companies.get(i).getCompanyName());
            map.fastPutAsync("catchPhrase", companies.get(i).getCatchPhrase());
            map.fastPutAsync("buzzWord", companies.get(i).getBuzzWord());
            map.fastPutAsync("industry", companies.get(i).getIndustry());
            map.fastPutAsync("companyLocation", companies.get(i).getCompanyLocation());
        }
        BatchResult<?> result = batch.execute();
        System.out.println(result.getResponses());
    }

    public void getCompany(List<String> companiesIds) throws ExecutionException, InterruptedException {

        for (String companiesId : companiesIds) {
            RFuture<Map<Object, Object>> allAsync = client.getMap("company" + DELIMITER + companiesId).getAllAsync(Set.of("companyName", "catchPhrase", "buzzWord", "industry", "companyLocation"));
            System.out.println(LocalDateTime.now());
            System.out.println(allAsync.get());
        }

    }

    public void closeConnection() {
        client.shutdown();
    }
}
