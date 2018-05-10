package com.westf.securityd.authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westf.securityd.propertites.BroswerProperties;
import com.westf.securityd.propertites.LoginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WestfAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());




    @Autowired
    ObjectMapper mapper;

    @Autowired
    private BroswerProperties broswerProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {


        logger.info("登陆失败");

        logger.info(broswerProperties.getLoginType().toString());
        if(LoginType.JSON.equals(broswerProperties.getLoginType())) {

            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }else
        {
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }

    }
}
