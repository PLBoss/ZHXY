package com.fox.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.zhxy.mapper.gradeMapper;
import com.fox.zhxy.pojo.grade;
import com.fox.zhxy.service.gradeService;
import freemarker.template.utility.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("gradeServiceImpl")
@Transactional
public class gradeServiceImpl extends ServiceImpl<gradeMapper, grade> implements gradeService {
    @Override
    public IPage<grade> getGradeByOpr(Page<grade> page, String gradeName) {
        QueryWrapper<grade> queryWrapper=new QueryWrapper();
        //判断gradeName是否为空，不为空就进行模糊查询，
//        注意选择spring中的StringUtils
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name",gradeName);
        }
        //根据id进行降序排列
        queryWrapper.orderByDesc("id");
        
        //转换成page对象
        Page<grade> gradePage = baseMapper.selectPage(page, queryWrapper);


        return gradePage;
    }

    @Override
    @ApiOperation("查询年级信息")
    public List<grade> getGrades() {
        return baseMapper.selectList(null);//没有查询条件，就是查询所有年级

    }
}
