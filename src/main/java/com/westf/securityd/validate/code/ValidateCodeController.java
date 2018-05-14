package com.westf.securityd.validate.code;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.westf.securityd.util.VerifyCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    DefaultSmsCodeSender defaultSmsCodeSender;


    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ImageCode imageCode = createImageCode(request);

        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


    @GetMapping("/code/sms")//短信验证码
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ValidateCode validateCode = createSmsCheckCode(request);

        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,validateCode);

        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        defaultSmsCodeSender.send(mobile,validateCode.getCode());

    }

    private ValidateCode createSmsCheckCode(HttpServletRequest request) {

        String code = RandomStringUtils.randomNumeric(4);//长度为4
        return  new ValidateCode(code,30);//30秒过期
    }


    private ImageCode createImageCode(HttpServletRequest request) {

        VerifyCode verifyCode = new VerifyCode();
        return new ImageCode(verifyCode.getImage(),verifyCode.getText(),60);

    }
}
