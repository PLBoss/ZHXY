package com.fox.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.zhxy.pojo.student;
import com.fox.zhxy.service.studentService;
import com.fox.zhxy.util.MD5;
import com.fox.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags = "学生信息控制器")
@RestController
@RequestMapping ("/sms/studentController")
//01/sms/studentController/getStudentByOpr/1/3
public class StudentController {





    @ApiOperation("删除学生信息")
    ///studentController/delStudentById
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
           @ApiParam("要删除的学生编号JSON数组") @RequestBody List<Integer> ids
            ){

        studentService.removeByIds(ids);

        return Result.ok();
    }
    @ApiOperation("更新学生信息")
    ///studentController/addOrUpdateStudent
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
           @ApiParam("要修改的学生信息JSON") @RequestBody student student
    ){
        //这里注意，应将添加信息里的密码进行加密，也就是不带id的json
        Integer id = student.getId();
        if (id==null || id==0){
           student.setPassword(MD5.encrypt(student.getPassword()));
       }
        studentService.saveOrUpdate(student);


        return Result.ok();
    }


    @Autowired
    private studentService studentService;
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    @ApiOperation("进行学生信息查询与分页")
    public Result getStudentByOpr(
           @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
           @ApiParam("页码大小")   @PathVariable("pageSize") Integer pageSize,
           @ApiParam("查询的条件") student student

    ){
        //建立学生的page对象
        Page<student> page =new Page<>(pageNo,pageSize);
        //服务层进行查询
       IPage<student> pageRs= studentService.getStudentByOpr(page,student);
        //封装
        return Result.ok(pageRs);
    }
}
