package com.example.spring.securityjwt.module.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeUser implements UserDetails {

   private final String userName;
   private final String password;
   private final boolean active;
//   private final List<GrantedAuthority> authorities;

    public EmployeeUser(Employee employee) {
        this.userName = employee.getName();
        this.password = employee.getPassword();
        this.active = true;
//        this.authorities = employee.getRoles().stream()
//                .map(r -> new SimpleGrantedAuthority(r.getName().name())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
