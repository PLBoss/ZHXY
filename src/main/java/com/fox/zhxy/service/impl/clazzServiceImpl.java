package com.fox.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.zhxy.mapper.clazzMapper;
import com.fox.zhxy.pojo.clazz;
import com.fox.zhxy.pojo.teacher;
import com.fox.zhxy.service.clazzService;
import com.fox.zhxy.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("clazzServiceImpl")
@Transactional
public class clazzServiceImpl extends ServiceImpl<clazzMapper,clazz> implements clazzService {

    @Override
    public IPage<clazz> getClassByOpr(Page<clazz> page, clazz clazz) {
        QueryWrapper<clazz> queryWrapper =new QueryWrapper<>();
        //从lazz中获取年级名gradeName,然后进行模糊查询
        String gradeName = clazz.getGradeName();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("grade_name",gradeName);

        }
        String name = clazz.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);

        }
        //根据id进行排序
        queryWrapper.orderByDesc("id");

        Page<clazz> clazzPage = baseMapper.selectPage(page, queryWrapper);

        return clazzPage;
    }

    @Override
    public List<clazz> getClazz() {
      return baseMapper.selectList(null);


    }
}
