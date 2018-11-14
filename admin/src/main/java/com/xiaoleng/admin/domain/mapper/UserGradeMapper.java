package com.xiaoleng.admin.domain.mapper;


import com.xiaoleng.admin.domain.po.UserGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserGradeMapper {

    UserGrade getUserGradeByUserId(@Param("userId") Integer userId);
}
