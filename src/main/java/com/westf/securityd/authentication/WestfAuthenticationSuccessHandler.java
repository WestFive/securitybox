package com.westf.securityd.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westf.securityd.propertites.BroswerProperties;
import com.westf.securityd.propertites.LoginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    BroswerProperties broswerProperties;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        logger.info("登陆成功");

        if(LoginType.JSON.equals(broswerProperties.getLoginType())) {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication.getDetails())); //转成JSON对象转回给响应

        }else {

            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }

    }
}
