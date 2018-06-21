package com.lichaobao.dropbox.model.fun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */

/**
 * 获得配置的ip
 */
@Configuration
@ConfigurationProperties(prefix = "fastdfs")
public class IpProperties {

    private  String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
