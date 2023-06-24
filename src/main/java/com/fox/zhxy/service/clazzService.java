package com.fox.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.zhxy.pojo.clazz;
import com.fox.zhxy.pojo.teacher;

import java.util.List;

public interface clazzService extends IService<clazz> {
    IPage<clazz> getClassByOpr(Page<clazz> page, clazz clazz);

    List<clazz> getClazz();
}

