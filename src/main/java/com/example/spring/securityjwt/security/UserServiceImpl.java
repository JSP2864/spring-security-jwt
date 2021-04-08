package com.example.spring.securityjwt.security;


import com.example.spring.securityjwt.module.domain.Employee;
import com.example.spring.securityjwt.module.domain.EmployeeUser;
import com.example.spring.securityjwt.module.domain.Role;
import com.example.spring.securityjwt.module.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user =  employeeRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
//        return new EmployeeUser(user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthority(user));

    }

    private Set<SimpleGrantedAuthority> getAuthority(Employee user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority( role.getName()));
        });
        return authorities;
    }
//
//    public List<User> findAll() {
//        List<User> list = new ArrayList<>();
//        employeeRepository.findAll().iterator().forEachRemaining(list::add);
//        return list;
//    }

//    @Override
//    public User findOne(String username) {
//        return employeeRepository.findByUserName(username);
//    }

//    @Override
//    public User save(UserDto user) {
//
//        User nUser = user.getUserFromDto();
//        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//
//        Role role = roleService.findByName("USER");
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(role);
//
//        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
//            role = roleService.findByName("ADMIN");
//            roleSet.add(role);
//        }
//
//        nUser.setRoles(roleSet);
//        return userDao.save(nUser);
//    }
}
