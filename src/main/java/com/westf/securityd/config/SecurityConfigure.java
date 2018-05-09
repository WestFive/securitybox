package com.westf.securityd.config;

import com.westf.securityd.authentication.WestfAuthenticationFailureHandler;
import com.westf.securityd.authentication.WestfAuthenticationSuccessHandler;
import com.westf.securityd.propertites.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class  SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();//可以返回自己实现的passwordEncoder
    }

    @Autowired
    WestfAuthenticationSuccessHandler westfAuthenticationSuccessHandler;

    @Autowired
    WestfAuthenticationFailureHandler westfAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.formLogin()
                .successHandler(westfAuthenticationSuccessHandler)
                .failureHandler(westfAuthenticationFailureHandler)
                .loginPage("/authentication/require") // loginPage or not html
                .loginProcessingUrl("/authentication/form")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",securityProperties.getBroswerProperties().getLoginPage()).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();


    }
}
