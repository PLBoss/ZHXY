package com.fox.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.zhxy.pojo.Admin;
import com.fox.zhxy.service.AdminService;
import com.fox.zhxy.util.MD5;
import com.fox.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//减少@RespondeBody在每个方法中的编写，直接进行异步交互进行返回数据
@RestController
//请求业务路径
@Api(tags = "管理员信息控制器")
@RequestMapping ("/sms/adminController")

public class AdminController {

    //ms/adminController/saveOrUpdateAdmin
    @ApiOperation("新增或修改用户信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("管理员信息") @RequestBody Admin admin){


        //对保存的密码信息进行加密
        Integer id = admin.getId();
        if (null==id ||id==0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }


        adminService.saveOrUpdate(admin);


        return  Result.ok();
    }


    ///sms/adminController/deleteAdmin
    @ApiOperation("删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
           @ApiParam("要删除的id数组") @RequestBody List<Integer> ids){

        adminService.removeByIds(ids);

        return Result.ok();



    }


    //sms/adminController/getAllAdmin/1/3

    @Autowired
    private AdminService adminService;
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    @ApiOperation("管理员信息分页查询")
    public Result getAllAdmin(
           @ApiParam("页面序号") @PathVariable("pageNo") Integer pageNo,
           @ApiParam("页面大小") @PathVariable("pageSize") Integer pageSize,
           @ApiParam("管理员名字") String adminName

    ){
        Page<Admin> page=new Page<>(pageNo,pageSize);

        IPage<Admin> pageRs= adminService.getAdminByOpr(page,adminName);



        return Result.ok(pageRs);
    }
}
