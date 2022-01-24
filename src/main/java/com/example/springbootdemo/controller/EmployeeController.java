package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.Employee;
import com.example.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    //build get all employees REST API
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String emailId){
        try {
            List<Employee> employeeList = new ArrayList<>();
            if (emailId == null)
                employeeList.addAll(employeeRepository.findAll());
            else
                employeeList.add(employeeRepository.findByEmailId(emailId));

            if (employeeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //build create employee REST API
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee){
        try {
            employeeRepository.save(new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailId()));
            return new ResponseEntity<>("Employee was created successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id);
        if (employee != null){
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //build update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
        Employee updatedEmployee = employeeRepository.findById(id);

        if (updatedEmployee != null){
            updatedEmployee.setFirstName(employeeDetails.getFirstName());
            updatedEmployee.setLastName(employeeDetails.getLastName());
            updatedEmployee.setEmailId(employeeDetails.getEmailId());

            employeeRepository.update(updatedEmployee);
            return new ResponseEntity<>("Employee was updated successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Cannot find employee with id = " + id, HttpStatus.NOT_FOUND);
        }
    }

    //build delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id){
        try {
            int result = employeeRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find employee with id = " + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Employee was deleted successfully.", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Cannot delete employee.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //build delete all employees REST API
    @DeleteMapping
    public ResponseEntity<String> deleteAllEmployees(){
        try {
            int numRows = employeeRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Employee(s) successfully.", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Cannot delete employee(s).", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
