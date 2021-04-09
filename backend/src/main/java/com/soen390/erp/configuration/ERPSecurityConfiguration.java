package com.soen390.erp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class ERPSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/securityNone").permitAll();
        http.csrf().disable() //todo: this will have to be changed later for additional security
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").authenticated()
                .anyRequest().authenticated()
                .and()
//                .formLogin().and()
                .httpBasic();


//                .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout", "GET"))
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout"))
                .logoutSuccessUrl("/securityNone")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.cors();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
