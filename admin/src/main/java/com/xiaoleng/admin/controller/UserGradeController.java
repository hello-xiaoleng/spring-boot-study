package com.xiaoleng.admin.controller;

import com.xiaoleng.admin.biz.UserGradeServiceImpl;
import com.xiaoleng.admin.domain.mapper.UserGradeMapper;
import com.xiaoleng.admin.domain.po.UserGrade;
import com.xiaoleng.admin.domain.repository.UserGradeRepository;
import com.xiaoleng.admin.domain.repository.UserGradereJpaRpository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@Slf4j
public class UserGradeController {

    @Autowired
    private UserGradereJpaRpository userGradereJpaRpository;

    @Autowired
    private UserGradeRepository userGradeRepositoryEntityManager;

    @Autowired
    private UserGradeMapper userGradeMapper;

    @Autowired
    private UserGradeServiceImpl userGradeService;


    @ResponseBody
    @RequestMapping(value = "/user.grade.get", method = RequestMethod.GET)
    public UserGrade getUserGrade(@RequestParam("userId") String userId) {
        Optional<UserGrade> userGrade = userGradereJpaRpository.findById(Integer.valueOf(userId));
        return userGrade.isPresent() ? userGrade.get() : null;
    }


    @ResponseBody
    @RequestMapping(value = "/user.grade.get2", method = RequestMethod.GET)
    public UserGrade getUserGrade2(@RequestParam("userId") String userId) {
        return userGradeRepositoryEntityManager.findUserById(Integer.valueOf(userId));

    }

    @ResponseBody
    @RequestMapping(value = "/user.grade.get3", method = RequestMethod.GET)
    public UserGrade getUserGrade3(@RequestParam("userId") String userId) {
        return userGradeMapper.getUserGradeByUserId(Integer.valueOf(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/user.grade.get4", method = RequestMethod.GET)
    @Cacheable(value = "name", cacheManager = "myCacheManager", key = "'UserGradeController_'+'getUserGrade4'+#userId")
    public String getUserGrade4(@RequestParam("userId") String userId) {
        UserGrade user = userGradeMapper.getUserGradeByUserId(Integer.valueOf(userId));
        log.info("getUserGradeByUserId,userId is:{}", user.getUserId());
        return user == null ? "" : String.valueOf(user.getGrade());
    }

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("userName") String userName) {
        return userGradeService.userGreet(userName);
    }
}
