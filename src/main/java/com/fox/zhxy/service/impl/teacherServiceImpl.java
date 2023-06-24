package com.fox.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.zhxy.mapper.teacherMapper;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.pojo.teacher;
import com.fox.zhxy.service.teacherService;
import com.fox.zhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("teacherServiceImpl")
@Transactional
public class teacherServiceImpl extends ServiceImpl<teacherMapper, teacher> implements teacherService {
    @Override
    public teacher login(LoginFrom loginFrom) {
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginFrom.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));

        teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public teacher getTeacherById(Long userId) {
        QueryWrapper<teacher> queryWrapper=new QueryWrapper<teacher>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<teacher> getTeacherByOpr(Page<teacher> page, teacher teacher) {
        QueryWrapper<teacher> queryWrapper=new QueryWrapper<>();
        String clazzName = teacher.getClazzName();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.like("clazz_name",clazzName);
        }

        String name = teacher.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }

        queryWrapper.orderByDesc("id");


        Page<teacher> teacherPage=baseMapper.selectPage(page,queryWrapper);

        return teacherPage;
    }


}
