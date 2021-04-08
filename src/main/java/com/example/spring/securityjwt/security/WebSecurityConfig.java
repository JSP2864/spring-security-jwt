package com.example.spring.securityjwt.security;


import com.example.spring.securityjwt.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserServiceImpl userDetailsService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    /**
     * AuthenticationManager needs to know where the user’s username and password is stored.
     * Passed AuthenticationManager to configure method of spring security so AuthenticationManager
     * can know where the username and password is saved.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    public void AddCorsMappings(CorsRegistr registry){
//        registry.
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/app/rest/authenticate", "/app/rest/register").permitAll()
//                .antMatchers("/app/rest/get_employee_detail").permitAll()
//                .antMatchers("/app/rest/save_employee").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        /**
         * Because we are using JWT to store roles,
         * we need to translate that into something that Spring Security can understand.
         * The JWT Token needs to be parsed to fetch roles that the SpringSecurityContext needs
         * to become aware of before it goes on to check if the API’s permissions will allow it
         */
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security uses  an AuthenticationManager(Interface) to validate if a given user has the right credentials
     * (based on username and password).
     *  AuthenticationManager Interface has exactly one method authenticate which is called
     * to verify if the username and password provided by a user are truthy.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

}
