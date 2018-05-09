package com.westf.securityd.propertites;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/*开启配置读取*/
@Configuration
@EnableConfigurationProperties(BroswerProperties.class)
public class SecurityCoreConfig {

}
