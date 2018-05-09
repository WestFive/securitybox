package com.westf.securityd.controller;

import com.westf.securityd.bean.SimpleResponse;
import com.westf.securityd.propertites.BroswerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BroswerSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();//跳转工具

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    BroswerProperties broswerProperties;


    //当需要身份认证时跳转到此
    @RequestMapping("/authentication/require")
    @ResponseStatus(code  = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthtication(HttpServletRequest request, HttpServletResponse response) throws IOException{

        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(null!=savedRequest){
            String targetUrl = savedRequest.getRedirectUrl();

            logger.info("引发跳转的请求是"+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html"))
            {
                redirectStrategy.sendRedirect(request,response,broswerProperties.getLoginPage());
                logger.info("跳转完成");
            }

        }
        return  new SimpleResponse("访问的服务需要身份认证.");
    }

}
