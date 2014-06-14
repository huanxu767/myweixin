package com.xh.support;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.xh.utils.WebAppConfig;


public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {  
    private static final String key = "0412026230720391";  
  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
        throws BeansException {  
            try {  
                Des des = new Des();  
                String username = props.getProperty("jdbc.user");  
                if (username != null) {  
                    props.setProperty("jdbc.user", des.Decrypt(username, des.hex2byte(key)));  
                }  
                  
                String password = props.getProperty("jdbc.password");  
                if (password != null) {  
                    props.setProperty("jdbc.password", des.Decrypt(password, des.hex2byte(key)));  
                }  
                  
                String url = props.getProperty("jdbc.jdbcUrl");  
                if (url != null) {  
                    props.setProperty("jdbc.jdbcUrl", des.Decrypt(url, des.hex2byte(key)));  
                }  
                  
                String driverClassName = props.getProperty("jdbc.driverClass");  
                if(driverClassName != null){  
                    props.setProperty("jdbc.driverClass", des.Decrypt(driverClassName, des.hex2byte(key)));  
                }  
                  
                super.processProperties(beanFactory, props);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new BeanInitializationException(e.getMessage());  
            }  
            for (Object keyName : props.keySet()) {  
            	String keyStr = keyName.toString();  
            	String value = props.getProperty(keyStr);  
            	WebAppConfig.GLOBAL_CONFIG_PROPERTIES.put(keyStr, value);  
            }  
        } 
}
