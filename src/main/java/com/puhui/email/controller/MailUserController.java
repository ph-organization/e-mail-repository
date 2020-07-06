package com.puhui.email.controller;


import com.puhui.email.entity.MailUser;
import com.puhui.email.service.MailUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: 杨利华
 * @date: 2020/7/5
 */
@RestController
@RequestMapping(value = "/mailUser")
public class MailUserController {

    //邮件用户接口注入
    @Autowired
    MailUserService MailUserService;

    //添加用户
    @PostMapping(value = "/addUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生年月（2020-01-02）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "号码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "result", value = "发送结果", required = true, dataType = "Boolean", paramType = "query")
    })
    public void addMailUser(MailUser mailUser) throws Exception {
        MailUserService.addMailUser(mailUser);
    }

    //查询用户
    @ApiOperation("根据ID查询")
    @GetMapping(value = "/queryById")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "query")
    )
    public MailUser queryMailUser(Integer id) {
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
            @ApiImplicitParam(name = "result", value = "发送结果", required = true, dataType = "Boolean", paramType = "query")
    })
    @PutMapping(value = "/upDateUser")

    public void upDateMailUser(MailUser mailUser) throws Exception{
        MailUserService.updateMailUser(mailUser);
        //修改邮件用户时删除邮件记录
    }
    //修改多个用户
//    @ApiOperation("修改多个用户")
//    @PutMapping(value = "/upDateUsers")
//    public void upDateMailUsers(List<MailUser> list){
//        MailUserService.updateMailUsers(list);
//    }


    //删除用户
    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/deleteById")
    public void deleteMailUser(int id){
        MailUserService.deleteMailUser(id);
        //邮件记录删除
    }


}
