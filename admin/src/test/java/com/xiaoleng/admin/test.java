package com.xiaoleng.admin;

import com.xiaoleng.admin.domain.mapper.UserGradeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class test {

    @Resource(name = "dataSource")
    private DataSource myDataSource;

    @Autowired
    private UserGradeMapper userGradeMapper;

    /**
     * 测试dataSource配置
     * 数据库连接是否正常
     */

    @Test
    public void contextLoads() {
        //执行SQL,输出查到的数据
        JdbcTemplate jdbcTemplate = new JdbcTemplate(myDataSource);
        List<?> resultList = jdbcTemplate.queryForList("select * from User_Grades");
        System.out.println("===>>>>>>>>>>>" + resultList);
    }

    @Test
    public void testMybatis() {
        userGradeMapper.getUserGradeByUserId(1);

    }
}
