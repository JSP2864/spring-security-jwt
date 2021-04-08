package com.example.spring.securityjwt.module.service;

import com.example.spring.securityjwt.module.domain.ERole;
import com.example.spring.securityjwt.module.domain.Employee;
import com.example.spring.securityjwt.module.domain.Role;
import com.example.spring.securityjwt.module.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    public Employee getEmployee(Long id){
       return employeeRepository.getEmployee(id);
    }

    public Employee saveEmployee(String name,String password){
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPassword(password);
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setId(8L);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        employee.setRoles(roleSet);
       return employeeRepository.save(employee);
    }
}
