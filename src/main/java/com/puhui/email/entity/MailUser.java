package com.puhui.email.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @description:  邮件用户实体
 * @author: 杨利华
 * @date: 2020/7/4
 */
@Data   //相当于加入getset方法
@AllArgsConstructor  //全参构造方法
@NoArgsConstructor
@ToString   //重写tostring方法，引入Lombok简化代码
@Entity
@Table(name = "mail_user")
//解决id查询对象出现异常
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class MailUser {

    //用户id
    @ApiModelProperty (value = "用户id",example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;
    //用户姓名
    @ApiModelProperty("用户名")
    @NotNull
    @NotBlank(message = "用户名不能为空")
    @Column (name = "name")
    private String name;
    //用户性别
    @ApiModelProperty("用户性别")
    @Column (name = "sex")
    private String sex;
    //出生年月
    @ApiModelProperty("用户生日")
    @Column (name = "birthday")
    private String birthday;
    //邮件
    @NotNull
    @NotBlank (message = "邮箱不能为空")
    @Column (name = "email")
    @ApiModelProperty("邮箱账号")
    private String email;
    //手机号
    @NotNull
    @ApiModelProperty("手机号")
    @Column (name = "phone")
    private String phone;
    //密码
    @NotNull
    @ApiModelProperty("账户密码")
    @Column (name = "pwd")
    private String pwd;
    //地址
    @ApiModelProperty("家庭住址")
    @Column (name = "address")
    private String address;
    //发送结果
    @ApiModelProperty("邮件发送结果")
    @Column (name = "result")
    private String result;
    //邮件内容
    @ApiModelProperty("邮件内容")
    @Column (name = "content")
    private String content;
    //邮件主题
    @ApiModelProperty("邮件主题")
    @Column (name = "topic")
    private String topic;

    //用于测试修改用户
    public MailUser(int id,String name,String sex,String birthday,String email,String phone,String pwd,String address) {
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.birthday=birthday;
        this.email=email;
        this.phone=phone;
        this.pwd=pwd;
        this.address=address;
    }
    //测试保存用户
    public MailUser(String name,String sex,String birthday,String email,String phone,String pwd,String address) {
        this.name=name;
        this.sex=sex;
        this.birthday=birthday;
        this.email=email;
        this.phone=phone;
        this.pwd=pwd;
        this.address=address;
    }
}
