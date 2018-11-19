package com.xiaoleng.admin.biz;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoleng.api.UserDubboService;
import org.springframework.stereotype.Service;

@Service
public class UserGradeServiceImpl {

    @Reference(version = "1.0.0")
    private UserDubboService userDubboService;

    public String userGreet(String userName) {
        return userDubboService.hello(userName);
    }
}

