package com.example.springbootdemo.repository;

import com.example.springbootdemo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Employee employee) {
        String sqlInsert = "INSERT into employees (id, email_id, first_name, last_name) VALUES (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sqlInsert, employee.getId(), employee.getEmailId(), employee.getFirstName(), employee.getLastName());

        return result;
    }

    @Override
    public int update(Employee employee) {
        String sqlUpdate = "UPDATE employees SET email_id = ?, first_name = ?, last_name = ? WHERE id = ?";
        int result = jdbcTemplate.update(sqlUpdate, employee.getEmailId(), employee.getFirstName(), employee.getLastName(), employee.getId());

        return result;
    }

    @Override
    public Employee findById(Long id) {
        try {
            String sqlRetrieveElementById = "SELECT * FROM employees WHERE id = " + id;
            Employee employee = jdbcTemplate.queryForObject(sqlRetrieveElementById, BeanPropertyRowMapper.newInstance(Employee.class));
            return employee;
        }
        catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        String sqlDeleteById = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate.update(sqlDeleteById, id);
    }

    @Override
    public List<Employee> findAll() {
        String sqlSelect = "SELECT * FROM employees";
        List<Employee> employeeList = jdbcTemplate.query(sqlSelect, BeanPropertyRowMapper.newInstance(Employee.class));

        return employeeList;
    }

    @Override
    public Employee findByEmailId(String emailId) {
        String sqlFindByEmailId = "SELECT * from employees WHERE email_id LIKE '%" + emailId + "%'";
        Employee employee =  jdbcTemplate.queryForObject(sqlFindByEmailId, BeanPropertyRowMapper.newInstance(Employee.class));
        return employee;
    }

    @Override
    public int deleteAll() {
        String sqlDeleteAll = "DELETE from employees";
        return jdbcTemplate.update(sqlDeleteAll);
    }
}


