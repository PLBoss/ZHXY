package com.fox.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.pojo.teacher;

import java.util.List;

public interface teacherService extends IService<teacher> {




    teacher login(LoginFrom loginFrom);

    teacher getTeacherById(Long userId);


    IPage<teacher> getTeacherByOpr(Page<teacher> page, teacher teacher);
}
