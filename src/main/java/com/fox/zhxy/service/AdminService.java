package com.fox.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.zhxy.pojo.Admin;
import com.fox.zhxy.pojo.LoginFrom;

public interface AdminService extends IService<Admin> {
  

    Admin login(LoginFrom loginFrom);

    Admin getAdminById(Long userId);


    IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName);
}
