package com.fox.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.zhxy.pojo.grade;
import com.fox.zhxy.service.gradeService;
import com.fox.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/sms/gradeController")
@Api(tags = "年级控制器")
public class GradeController {
    @Autowired
    private gradeService gradeService;

    @ApiOperation("获取所以年级信息")
    @GetMapping("/getGrades")
    public Result getGrades(){
       List<grade> grades= gradeService.getGrades();


        return  Result.ok(grades);
    }


//    删除与批量删除
    @ApiOperation("删除与批量删除")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(

            @ApiParam("要删除的id号JSON集合") @RequestBody List<Integer> ids){

        //调用服务层进行删除
        gradeService.removeByIds(ids);


        return  Result.ok();
    }



    //年级信息添加与修改，添加请求头不带id,修改请求头带上id
    @ApiOperation("进行年级信息的增加或修改，加请求头不带id,修改请求头带上id")
    @PostMapping ("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("JSON格式年级对象") @RequestBody grade grade){
//        接受参数


//        调用服务层方法完成增减或者修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }


    //年级信息查询与分页
    @ApiOperation("年级信息查询与分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
           @ApiParam("第几页") @PathVariable("pageNo") Integer pageNo,
           @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
           @ApiParam("模糊查询的年级名称") String gradeName

    ){
        //分页，带条件查询
        Page<grade> page=new Page<>(pageNo,pageSize);
        //通过服务层进程查询
        IPage<grade> pageRs=gradeService.getGradeByOpr(page,gradeName);

        //封装result对象并返回
        return  Result.ok(pageRs);

    }
}
