package com.fox.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.zhxy.pojo.clazz;
import com.fox.zhxy.pojo.teacher;
import com.fox.zhxy.service.clazzService;
import com.fox.zhxy.service.teacherService;
import com.fox.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/sms/clazzController")
@Api(tags = "班级控制器")
public class ClazzController {
    @ApiOperation("删除或者批量删除")
    //1/sms/clazzController/deleteClazz
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(

           @ApiParam("要删除的id列表") @RequestBody List<Integer > ids
    ){
        clazzService.removeByIds(ids);

        return Result.ok();


    }
    @ApiOperation("添加或修改班级信息")
    //sms/clazzController/saveOrUpdateClazz
    //添加或修改班级信息
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(

            @ApiParam("JSON格式的班级信息") @RequestBody clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);


        return Result.ok();
    }

    ///sms/clazzController/getClazzs

    @Autowired
    private clazzService clazzService;
    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
         List<clazz> claszzs=clazzService.getClazz();


        return  Result.ok(claszzs);
    }

    //clazzController/getClazzsByOpr/1/3
    //1/3?gradeName=%E4%B8%89%E5%B9%B4%E7%BA%A7&name=%E4%B8%80

    @ApiOperation("进行分页和查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
           @ApiParam("页面序号") @PathVariable("pageNo") Integer pageNo,
           @ApiParam("页面大小") @PathVariable("pageSize") Integer pageSize,
           @ApiParam("分页查询的条件包括name,grade_name") clazz clazz


    ){
        //建立分页
        Page<clazz> page = new Page<>(pageNo, pageSize);
        //在服务层进行查询
        IPage<clazz> pageRs=clazzService.getClassByOpr(page,clazz);

        return Result.ok(pageRs);


    }
}
