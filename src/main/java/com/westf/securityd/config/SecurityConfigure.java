package com.westf.securityd.config;

import com.westf.securityd.authentication.WestfAuthenticationFailureHandler;
import com.westf.securityd.authentication.WestfAuthenticationSuccessHandler;
import com.westf.securityd.mobile.SmsCodeAuthenticationSecurityConfig;
import com.westf.securityd.propertites.BroswerProperties;
import com.westf.securityd.validate.code.SmsCodeFilter;
import com.westf.securityd.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class  SecurityConfigure extends WebSecurityConfigurerAdapter {



    @Autowired
    private BroswerProperties broswerProperties;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();//可以返回自己实现的passwordEncoder
    }

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private DataSource dataSource;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true); //初始启动时取消注释 二次启动时注释此行
        return tokenRepository;
    }


    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;



    @Autowired
    WestfAuthenticationSuccessHandler westfAuthenticationSuccessHandler;

    @Autowired
    WestfAuthenticationFailureHandler westfAuthenticationFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter filter = new ValidateCodeFilter();
        filter.setAuthenticationFailureHandler(westfAuthenticationFailureHandler); //添加自定义filter

        SmsCodeFilter filter1 = new SmsCodeFilter();
        filter1.setAuthenticationFailureHandler(westfAuthenticationFailureHandler);


        http.addFilterBefore(filter1,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .successHandler(westfAuthenticationSuccessHandler)
                .failureHandler(westfAuthenticationFailureHandler)
                .loginPage("/authentication/require") // loginPage or not html
                .loginProcessingUrl("/authentication/form")
                .and()
                    .rememberMe().tokenRepository(persistentTokenRepository)
                    .tokenValiditySeconds(broswerProperties.getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                    .authorizeRequests()
                    .antMatchers("/authentication/require",broswerProperties.getLoginPage(),"/code/*","/temp").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .csrf().disable()
        .apply(smsCodeAuthenticationSecurityConfig);


    }
}
