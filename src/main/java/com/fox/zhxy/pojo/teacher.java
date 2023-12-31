package com.fox.zhxy.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("tb_teacher")
public class teacher {

    @TableId(value = "id",type = IdType.AUTO )
    private Integer id;
    private String tno;//教工号师
    private String name;
    private char gender;
    private  String password;
    private String email;
    private String  telephone;
    private  String address;
    private String portraitPath;//头像信息
    private  String clazzName;




}
