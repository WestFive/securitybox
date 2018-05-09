package com.westf.securityd.propertites;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "westf.security")
public class SecurityProperties {

    private BroswerProperties broswerProperties = new BroswerProperties();

    public BroswerProperties getBroswerProperties() {
        return broswerProperties;
    }

    public void setBroswerProperties(BroswerProperties broswerProperties) {
        this.broswerProperties = broswerProperties;
    }
}
