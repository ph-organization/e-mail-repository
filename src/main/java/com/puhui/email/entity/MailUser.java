package com.puhui.email.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @description:  邮件用户实体
 * @author: 杨利华
 * @date: 2020/7/4
 */
@Data   //相当于加入getset方法
@AllArgsConstructor  //全参构造方法
@ToString   //重写tostring方法，引入Lombok简化代码
@Entity
@Table(name = "mailuser")
public class MailUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column
    private String name;

    @Column
    private String sex;

    @Column
    private String birthday;

    @NotNull
    @Column
    private String email;

    @Column
    @NotNull
    private String phone;

    @Column
    private String pwd;

    @Column
    private String address;

    @Column
    private Boolean result;

    public MailUser() {
    }
}
