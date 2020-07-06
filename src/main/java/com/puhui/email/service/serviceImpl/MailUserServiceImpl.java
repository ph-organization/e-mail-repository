package com.puhui.email.service.serviceImpl;


import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:  邮件用户crud实现
 * @author: 杨利华
 * @date: 2020/7/5
 */
@Service
@Transactional
public class MailUserServiceImpl implements MailUserService {

    @Autowired
    MailUserMapper mailUserMapper;

    //创建对象用于加密保存数据/解密修改数据
    MailUser user=new MailUser();

    //用于封装随机产生的公钥与私钥
    Map<Integer, String> keyMap = new HashMap<Integer, String>();

    //添加用户
    @Override
    @CachePut(value = "mailuser",key = "#mailUser.id.toString()")
    public void addMailUser(MailUser mailUser) throws Exception{

        ////生成公钥私钥
        RSAEncryptUtil.genKeyPair(keyMap);
        //公钥加密
        String publickey = keyMap.get(0);

        user.setId(mailUser.getId());
        //加密
        user.setName(RSAEncryptUtil.encrypt(mailUser.getName(),publickey));
        user.setSex(mailUser.getSex());
        user.setEmail(RSAEncryptUtil.encrypt(mailUser.getEmail(),publickey));
        user.setPwd(RSAEncryptUtil.encrypt(mailUser.getPwd(),publickey));
        user.setBirthday(mailUser.getBirthday());
        user.setPhone(RSAEncryptUtil.encrypt(mailUser.getPhone(),publickey));
        user.setResult(mailUser.getResult());
        user.setAddress(RSAEncryptUtil.encrypt(mailUser.getAddress(),publickey));
        mailUserMapper.save(user);
    }

    //修改用户
    @Override
    @CacheEvict(value = "mailuser",key = "#mailUser.id.toString()")
    public void updateMailUser(MailUser mailUser) throws Exception {

//        SimpleDateFormat f=new SimpleDateFormat("yyy-MM-dd");
        ////生成公钥私钥
        RSAEncryptUtil.genKeyPair(keyMap);
        //公钥加密
        String publickey = keyMap.get(0);
        //私钥解密
        String privatekey = keyMap.get(1);

        List<MailUser> userList=queryAllMailUser();

        for (MailUser mu:userList) {
            if (mu.getId()==mailUser.getId()){

                user.setId(mailUser.getId());
                //加密
                user.setName(RSAEncryptUtil.encrypt(mailUser.getName(),publickey));
                user.setSex(mailUser.getSex());
                user.setEmail(RSAEncryptUtil.encrypt(mailUser.getEmail(),publickey));
                user.setPwd(RSAEncryptUtil.encrypt(mailUser.getPwd(),publickey));
                user.setBirthday(mailUser.getBirthday());
                user.setPhone(RSAEncryptUtil.encrypt(mailUser.getPhone(),publickey));
                user.setResult(mailUser.getResult());
                user.setAddress(RSAEncryptUtil.encrypt(mailUser.getAddress(),publickey));
                mailUserMapper.saveAndFlush(user);
            }
        }
    }

    //修改多个用户
    @Override
    public void updateMailUsers(List<MailUser> list) {
        for (MailUser mailUser: list) {
            mailUserMapper.saveAndFlush(mailUser);
        }
    }

    //删除用户
    @Override
    @CacheEvict(value = "mailuser",key = "#id")
    public void deleteMailUser(Integer id) {
        mailUserMapper.deleteById(id);

    }
    //删除多个用户
    @Override
    public void deleteMailUsers(List<Integer> list) {
        for (int i:list) {
            mailUserMapper.deleteById(i);
        }
    }

    //查询用户
    @Override
    @Cacheable(value = "mailuser",key = "#id")
    public  MailUser queryMailUser(Integer id) {
        MailUser user = mailUserMapper.getOne(id);
        return user;
    }

    //查询全部用户
    @Override
    public List<MailUser> queryAllMailUser() {
        return mailUserMapper.findAll();
    }
}
