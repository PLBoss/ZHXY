package com.fox.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.pojo.student;

public interface studentService extends IService<student> {
   

    student login(LoginFrom loginFrom);

    student getStudentById(Long userId);

    IPage<student> getStudentByOpr(Page<student> page, student student);
}
