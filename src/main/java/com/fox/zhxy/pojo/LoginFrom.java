package com.fox.zhxy.pojo;


import lombok.Data;
/**
@project:ssm_ssm;
@description:用户信息登陆表单
 */


@Data
public class LoginFrom {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;





}
