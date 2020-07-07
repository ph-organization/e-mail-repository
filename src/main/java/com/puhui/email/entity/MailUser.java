package com.puhui.email.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

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
public class MailUser implements Serializable{

    //用户id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //用户姓名
    @NotNull
    private String name;
    //用户性别
    private String sex;
    //出生年月
    private String birthday;
    //邮件
    @NotNull
    private String email;
    //手机号
    @NotNull
    private String phone;
    //密码
    @NotNull
    private String pwd;
    //地址
    private String address;
    //发送结果
    private String result;
    //邮件内容
    private String content;
    //邮件主题
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
