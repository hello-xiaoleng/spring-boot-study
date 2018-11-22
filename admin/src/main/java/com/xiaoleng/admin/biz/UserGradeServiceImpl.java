package com.xiaoleng.admin.biz;

import com.xiaoleng.api.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGradeServiceImpl {

    @Autowired
    private UserDubboService userDubboService;

    public String userGreet(String userName) {
        return userDubboService.hello(userName);
    }
}

