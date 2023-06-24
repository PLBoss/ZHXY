package com.fox.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.zhxy.mapper.AdminMapper;
import com.fox.zhxy.pojo.Admin;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.service.AdminService;
import com.fox.zhxy.util.MD5;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("adminServiceImpl")//获取当前实现类的id，让Spring识别这个组件
@Transactional //输入控制
//利用ServiceImpl对IService进行方法实现
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    @Override
    public Admin login(LoginFrom loginFrom) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginFrom.getUsername());//查询条件拼接
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));//密码要经过MD5加密

        Admin admin = baseMapper.selectOne(queryWrapper);//数据库查找
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<Admin>();
        queryWrapper.eq("id",userId);

        return baseMapper.selectOne(queryWrapper);

    }


    @Override
    public IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName) {
        QueryWrapper queryWrapper=new QueryWrapper<>();

        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByDesc("id");

        Page<Admin> adminPage = baseMapper.selectPage(page, queryWrapper);

        return adminPage;
    }
}
