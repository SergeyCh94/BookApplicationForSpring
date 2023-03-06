package com.skypro.library.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/web").permitAll()
                .antMatchers("/api/book").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/book").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/book/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/book/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}