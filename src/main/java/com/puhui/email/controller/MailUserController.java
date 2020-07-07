package com.puhui.email.controller;


import com.puhui.email.entity.MailUser;

import com.puhui.email.entity.MailUserList;
import com.puhui.email.service.MailUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:  controller的curd
 * @author: 杨利华
 * @date: 2020/7/5
 */
@RestController
@ResponseBody
@RequestMapping(value = "/mailUser")
public class MailUserController {

    //邮件用户接口注入
    @Autowired
    MailUserService MailUserService;

    //添加用户
    @ApiOperation("用户添加")
    @PostMapping(value = "/addUser")
    //在swagger页面添加注释
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID（可不填）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生年月（2020-01-02）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "result", value = "发送结果(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "topic", value = "邮件主题(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "邮件内容(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
    })
    public void addMailUser(MailUser mailUser) throws Exception {
        System.out.println(mailUser);
        MailUserService.addMailUser(mailUser);
    }

    //查询用户
    @ApiOperation("根据ID查询")
    @GetMapping(value = "/queryById")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    public MailUser queryMailUser(Integer id) throws Exception {
        MailUser mailUser=MailUserService.queryMailUser(id);
        return mailUser;
    }

    //修改单个用户
    @ApiOperation("修改单个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生年月（2020-01-02）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "result", value = "发送结果(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "topic", value = "邮件主题(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "邮件内容(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
    })
    @PutMapping(value = "/upDateUser")
    public void upDateMailUser(MailUser mailUser) throws Exception{
        MailUserService.updateMailUser(mailUser);
    }
    //修改多个用户
    @ApiOperation("修改多个用户")
    @PutMapping(value = "/upDateUsers")
    public void upDateMailUsers(@RequestBody MailUserList list) throws Exception {
        MailUserService.updateMailUsers(list.getList());
    }


    //删除用户
    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/deleteById")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    public void deleteMailUser(int id){
        MailUserService.deleteMailUser(id);
    }
    //删除多个用户
    @ApiOperation("根据ID删除多个用户")
    @DeleteMapping(value = "/deletes")
    public void deleteMailUsers(@RequestParam(value = "list") List<Integer> list){
        MailUserService.deleteMailUsers(list);
    }


}
