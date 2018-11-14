package com.xiaoleng.admin.domain.po;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "USER_GRADES")
public class UserGrade {

    @Id
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "GRADE")
    private Integer grade;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;


}
