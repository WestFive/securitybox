package com.westf.securityd.config;

import com.westf.securityd.authentication.WestfAuthenticationFailureHandler;
import com.westf.securityd.authentication.WestfAuthenticationSuccessHandler;
import com.westf.securityd.propertites.BroswerProperties;
import com.westf.securityd.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class  SecurityConfigure extends WebSecurityConfigurerAdapter {



    @Autowired
    private BroswerProperties broswerProperties;

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
        ValidateCodeFilter filter = new ValidateCodeFilter();
        filter.setAuthenticationFailureHandler(westfAuthenticationFailureHandler); //添加自定义filter



        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .successHandler(westfAuthenticationSuccessHandler)
                .failureHandler(westfAuthenticationFailureHandler)
                .loginPage("/authentication/require") // loginPage or not html
                .loginProcessingUrl("/authentication/form")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",broswerProperties.getLoginPage(),"/code/image").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();


    }
}
