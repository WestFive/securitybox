package com.westf.securityd.validate.code;

public interface SmsCodeSender {

    void send(String mobile,String code);
}
