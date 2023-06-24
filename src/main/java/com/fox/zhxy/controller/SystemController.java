package com.fox.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fox.zhxy.pojo.Admin;
import com.fox.zhxy.pojo.LoginFrom;
import com.fox.zhxy.pojo.student;
import com.fox.zhxy.pojo.teacher;

import com.fox.zhxy.service.AdminService;
import com.fox.zhxy.service.studentService;
import com.fox.zhxy.service.teacherService;
import com.fox.zhxy.util.*;
import com.sun.deploy.net.HttpRequest;
import com.sun.javafx.collections.MappingChange;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/sms/system")
@Api(tags = "系统控制器")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private studentService studentService;
    @Autowired
    private teacherService teacherService;

    //sms/system/updatePwd/admin/123456
    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("输入的旧密码") @PathVariable("oldPwd") String oldPwd,
            @ApiParam("输入的新密码") @PathVariable("newPwd") String newPwd,
            @ApiParam("token口令")  @RequestHeader("token") String token
    ){

        //先要判断用户的token是否过期
        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("用户token过期，请重新登陆");
        }

        //利用token解析出用户类型，便于进入数据库进行查询
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        //将输入的明文密码进行加密，才能于 数据库中密文密码进行对比
        String enOldPwd = MD5.encrypt(oldPwd);
        String enNewPwd = MD5.encrypt(newPwd);

        switch (userType){

            case 1:
                //建立查询条件，然后进行数据库查询
                QueryWrapper<Admin> queryWrapper =new QueryWrapper<>();
                queryWrapper.eq("id",userId.intValue());
                queryWrapper.eq("password",enOldPwd);
                Admin admin = adminService.getOne(queryWrapper);
                if (admin!=null) {
                    //重新设置密码，然后将admin对象上传
                    admin.setPassword(enNewPwd);
                    adminService.saveOrUpdate(admin);

                }else{

                    return Result.fail().message("原密码有误，请重新输入");
                }
                break;
            case 2:
                QueryWrapper<student> sQueryWrapper=new QueryWrapper<>();
                sQueryWrapper.eq("id",userId.intValue());
                sQueryWrapper.eq("password",enOldPwd);
                student student = studentService.getOne(sQueryWrapper);
                if (student!=null) {
                    student.setPassword(enNewPwd);
                    studentService.saveOrUpdate(student);

                }
                else{
                    return Result.fail().message("原密码有误，请重新输入");

                }

                break;
            case 3:

                QueryWrapper tQueryWrapper=new QueryWrapper<>();
                tQueryWrapper.eq("id",userId);
                tQueryWrapper.eq("password",enOldPwd);
                teacher teacher = teacherService.getOne(tQueryWrapper);
                if (teacher!=null) {
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码有误，请重新输入");
                }

                break;


        }




        return  Result.ok();
    }



    @ApiOperation("上传图片")
    //t:9001/sms/system/headerImgUpload
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("multipartFile对象的图像文件") @RequestPart("multipartFile") MultipartFile multipartFile,
            HttpServletRequest request
    ){
        //对图片的进行重命名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();//取上传图片的原文件名
        int i = originalFilename.lastIndexOf(".");//取.的索引位置
        String newFileName=uuid+originalFilename.substring(i);//新图片名拼接

        //上传图片,一般使用第三方图片服务器，这里就使用本地即可,即本地项目文件夹
        String portraitPath="D:\\Desktop\\ZHXY\\zhxy\\target\\classes\\public\\upload\\"+newFileName;
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //响应图片路径
        String path="upload/"+newFileName;
        return  Result.ok(path);





    }

    //难点2：在pojo中student没有给出name,teacher的className与数据库中的clazz_name对应不上
    @GetMapping("/getInfo")
    @ApiOperation("根据用户登陆的token解析出用户的类型,跳转不同的页面")
    //接收用户登陆返回token,验证他是否过期,从请求头获取token消息
    public Result getInfoByToken(
            @ApiParam("从登陆请求头中获取的token口令") @RequestHeader("token") String token){
//        使用方法体的JwHelPer解析token是否过期，则返回True
        boolean expiration = JwtHelper.isExpiration(token);
//如果过期了，要返回状态码
        if(expiration){
            return Result.build(null,ResultCodeEnum.TOKEN_ERROR);
        }
//        从token解析出token解析出用户的id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String,Object> map=new LinkedHashMap<>();
//      根据用户的类型跳转不同的页面，返回不同的用户的信息
        switch (userType){

            case 1:
                Admin admin=adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                student student=studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                teacher teacher= teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;

        }


        return Result.ok(map);

    }




    @PostMapping("/login")
    @ApiOperation("用户账号密码校验和token口令的生成")
    public Result login(
            @ApiParam("用户登陆输入的信息表单") @RequestBody LoginFrom loginFrom,
            HttpServletRequest request){

        //验证码校验

        HttpSession session = request.getSession();
        String sessionVerificode = (String)session.getAttribute("verificode");
        //难点1:getVerifiCode命名问题，getVerificode无法正常使用
        String loginVerificode = loginFrom.getVerifiCode();
        //判断session是否失效
        if("".equals(sessionVerificode) || null==sessionVerificode)
        {
                return Result.fail().message("验证码失效，请刷新后重试");
        }
        //对比密码是相同,不区分大小写


        if (!sessionVerificode.equalsIgnoreCase(loginVerificode)){
            return Result.fail().message("验证码错误，请刷新后重试");

        }
  //将已用过的验证码移除session域
        session.removeAttribute("verificode");
        //准备一个Maping用于存放响应的数据
        Map<String,Object> map=new LinkedHashMap<>();
        //用户类型校验,使用service取连接数据库，并进行查询
        switch (loginFrom.getUserType()){

            case 1:
                try {
                    Admin admin= adminService.login(loginFrom);
                    if (null!=admin) {
                        //如果用户不空，将用户的类型和id转换成一个密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken( admin.getId().longValue(), 1);
                        map.put("token",token);
                    }else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

            case 2:
                try {
                    student student= studentService.login(loginFrom);
                    if(null!=student){
                        String token = JwtHelper.createToken((long) student.getId(), 2);
                        map.put("token",token);
                    }else{
                        throw  new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();

                    return Result.fail().message(e.getMessage());
                }

            case 3:
                try {
                    teacher teacher= teacherService.login(loginFrom);
                    if(null!=teacher){
                        String token = JwtHelper.createToken((long) teacher.getId(), 3);
                        map.put("token",token);
                    }else{
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");


    }
    @GetMapping("/getVerifiCodeImage")
    @ApiOperation("获取验证码的图片")
    public void getVerifiCodeImage(
            HttpServletRequest request,HttpServletResponse response){
        //获取图片，使用工具类
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的数字
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //放入session域，后期进行验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verificode",verifiCode);
        //反馈到前端页面上
        try {
            ServletOutputStream outputStream = response.getOutputStream();//建立IO流
            ImageIO.write(verifiCodeImage,"JPEG",outputStream);//使用IO流将图片输出
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
