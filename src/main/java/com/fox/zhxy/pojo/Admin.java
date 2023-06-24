package com.fox.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//使用lomlok直接调用包进行无参构造方法，全参构造方法
@Data
@AllArgsConstructor
@NoArgsConstructor
//进行数据库映射
@TableName("tb_admin")
//根据数据库表，建立实体类

public class Admin {
    @TableId(value = "id",type = IdType.AUTO)

    private Integer id;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;//头像路径


}
