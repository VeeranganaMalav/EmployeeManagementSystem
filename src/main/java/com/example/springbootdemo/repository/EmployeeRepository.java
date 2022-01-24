package com.example.springbootdemo.repository;

import com.example.springbootdemo.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    int save(Employee employee);

    int update(Employee employee);

    Employee findById(Long id);

    int deleteById(Long id);

    List<Employee> findAll();

    Employee findByEmailId(String emailId);

    int deleteAll();
}
