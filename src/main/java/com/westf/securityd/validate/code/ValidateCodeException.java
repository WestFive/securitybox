package com.westf.securityd.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException  extends AuthenticationException{


    private static final long serialVersionUID = -3269998791804200252L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
