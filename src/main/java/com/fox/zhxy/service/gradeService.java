package com.fox.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.zhxy.pojo.grade;

import java.util.List;

public interface gradeService extends IService<grade> {
    IPage<grade> getGradeByOpr(Page<grade> page, String gradeName);

    List<grade> getGrades();
}
