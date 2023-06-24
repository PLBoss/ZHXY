package com.fox.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.zhxy.mapper.studentMapper;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.pojo.student;
import com.fox.zhxy.service.studentService;
import com.fox.zhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("studentServiceImpl")
@Transactional
public class studentServiceImpl extends ServiceImpl<studentMapper, student> implements studentService {
    @Override
    public student login(LoginFrom loginFrom) {
        QueryWrapper<student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginFrom.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));

        student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public student getStudentById(Long userId) {
        QueryWrapper<student> queryWrapper=new QueryWrapper<student>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<student> getStudentByOpr(Page<student> page, student student) {
        QueryWrapper<student> queryWrapper=new QueryWrapper<>();
        String clazzName = student.getClazzName();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.like("clazz_name",clazzName);

        }
        String name = student.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }

        queryWrapper.orderByDesc("id");

        Page<student> studentPage=baseMapper.selectPage(page,queryWrapper);

        return studentPage;
    }


}
