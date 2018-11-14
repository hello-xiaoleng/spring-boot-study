package com.xiaoleng.admin.domain.repository;

import com.xiaoleng.admin.domain.po.UserGrade;
import org.springframework.stereotype.Repository;

@Repository
public class UserGradeRepository extends BaseRepository<UserGrade> {

    public UserGrade findUserById(int userId) {
        String hql = "select u from UserGrade u where u.userId =:userId";
        return getSingleResult(entityManager.createQuery(hql, UserGrade.class).
                setParameter("userId", userId));
    }

}
