package com.xiaoleng.admin.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.xiaoleng.api.UserDubboService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {

    private static final String VERSION = "1.0.0";

    @Bean
    public ReferenceBean<UserDubboService> userDubboService() {
        ReferenceBean<UserDubboService> referenceBean = new ReferenceBean<>();
        referenceBean.setVersion(VERSION);
        referenceBean.setInterface(UserDubboService.class);
        return referenceBean;
    }


}
