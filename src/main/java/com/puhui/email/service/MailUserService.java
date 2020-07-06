package com.puhui.email.service;

import com.puhui.email.entity.MailUser;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description:  邮件用户crud
 * @author: 杨利华
 * @date: 2020/7/5
 */
public interface MailUserService {

    //添加用户
    public void addMailUser(MailUser mailUser) throws Exception;

    //修改用户
    public void updateMailUser(MailUser mailUser) throws NoSuchAlgorithmException, Exception;
    //修改多个用户
    public void updateMailUsers(List<MailUser> list);
    //删除用户
    public void deleteMailUser(Integer id);
    //删除多个用户
    public void deleteMailUsers(List<Integer> list);
    //查询用户
    public MailUser queryMailUser(Integer id);

    public List<MailUser> queryAllMailUser();

}
