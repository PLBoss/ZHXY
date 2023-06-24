package com.fox.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.zhxy.pojo.teacher;
import com.fox.zhxy.service.teacherService;
import com.fox.zhxy.util.MD5;
import com.fox.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/sms/teacherController")
@Api(tags = "老师信息控制器")

public class TeacherController {

    //1/sms/teacherController/deleteTeacher
    @ApiOperation("删除与批量删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
          @ApiParam("要删除的老师信息的ids数组")  @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);

        return Result.ok();


    }
    //s/teacherController/saveOrUpdateTeacher
    @ApiOperation("添加和更新教师")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("教师的信息") @RequestBody teacher teacher
            //接受老师参数信息

    ){
        Integer id = teacher.getId();
        if (id==null || id ==0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        //调用服务层进行处理
        teacherService.saveOrUpdate(teacher);


        return Result.ok();
    }

    //teacherController/getTeachers/1/3?name=sd&clazzName=四
    @Autowired
    private teacherService teacherService;
    @ApiOperation("教师信息查询与分页")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")

    public  Result  getTeachersByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            teacher teacher

    ){
        //建立分页
        Page<teacher> page =new Page<>(pageNo,pageSize);
//      服务层进行查询
       IPage<teacher> teacherRs= teacherService.getTeacherByOpr(page,teacher);




        return Result.ok(teacherRs);
    }



}
