package com.example.spring.securityjwt.module.repository;

import com.example.spring.securityjwt.module.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query(value="select e from  Employee e where e.id =?1")
    Employee getEmployee(Long id);

    @Query(value="select e from Employee e where e.name =?1")
    Employee findByUserName(String name);
}
