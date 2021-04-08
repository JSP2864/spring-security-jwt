package com.example.spring.securityjwt.module.controller;


import com.example.spring.securityjwt.module.domain.Employee;
import com.example.spring.securityjwt.module.service.EmployeeService;
import com.example.spring.securityjwt.security.UserServiceImpl;
import com.example.spring.securityjwt.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

@RestController
@RequestMapping(value="/app/rest")
public class EmployeeController {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private TokenProvider jwtTokenUtil;

    @Inject
    private UserServiceImpl userService;

        @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public ResponseEntity<?> generateToken(@RequestParam String name, @RequestParam String password) {
     try {
         final Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         name,
                         password
                 )
         );
         SecurityContextHolder.getContext().setAuthentication(authentication);
         final String token = jwtTokenUtil.generateToken(authentication);
         return ResponseEntity.ok(token);
     } catch (Exception e){
         System.out.println("exception"+e.getMessage());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }

    }

    @RequestMapping(value="/get_employee_detail",method = RequestMethod.GET)
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity getEmployeeDetail(@RequestParam Long id){
        System.out.println("id"+id);
        Employee employee = employeeService.getEmployee(id);
        return new ResponseEntity(employee,HttpStatus.OK);
    }


    @RequestMapping(value="/save_employee",method = RequestMethod.GET)
    public ResponseEntity getEmployeeDetail(@RequestParam String name,@RequestParam String password){
        System.out.println("id"+name);
        Employee employee = employeeService.saveEmployee(name,password);
        return new ResponseEntity(employee,HttpStatus.OK);
    }
}
