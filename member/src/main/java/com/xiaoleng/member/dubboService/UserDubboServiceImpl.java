package com.xiaoleng.member.dubboService;

import com.alibaba.dubbo.config.annotation.Service;
import com.xiaoleng.api.UserDubboService;

@Service(version = "1.0.0")
public class UserDubboServiceImpl implements UserDubboService {
    @Override
    public String hello(String name) {
        return "hello world" + name;
    }
}
