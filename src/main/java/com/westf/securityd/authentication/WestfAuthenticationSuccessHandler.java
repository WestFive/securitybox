package com.westf.securityd.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westf.securityd.propertites.LoginType;
import com.westf.securityd.propertites.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WestfAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();//跳转工具

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        logger.info("登陆成功");

        if(LoginType.JSON.equals(securityProperties.getBroswerProperties().getLoginType())) {

            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication.getDetails())); //转成JSON对象转回给响应
        }else {

            redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,securityProperties.getBroswerProperties().getDefaultPage());
            //super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }

    }
}
