package com.puhui.email;


import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailService;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.EncodeUtil;
import com.puhui.email.util.RSAEncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith (SpringRunner.class)
public class EMailApplicationTests {
    /**
     * 邮件用户的crud，redis记录测试
     * 杨利华
     */
    private static Map<Integer, String> map = EncodeUtil.readKey();
    @Autowired
    MailUserService mailUserService;
    @Autowired
    private MailUserMapper mailUserMapper;
    @Autowired
    private MailService mailService;
    //配置文件中的公钥
   // @Value ("${rsa.publicKey}")
    private String publicKey=map.get(1);
    //配置文件中的私钥
  //  @Value ("${rsa.privateKey}")
    private String privateKey=map.get(2);

    /**
     * 邹玉玺
     * 测试加密解密
     */
    @Test
    public void contextLoads() throws Exception {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
        //邮箱
        String email = "1003941268@qq.com";
        //生成公钥私钥
        RSAEncryptUtil.genKeyPair(keyMap);
        //公钥
        String publickey = keyMap.get(0);
        //私钥
        String privatekey = keyMap.get(1);
        //对邮箱进行加密
        String encrypt = RSAEncryptUtil.encrypt(email, publickey);
        System.out.println("加密后的数据" + encrypt);
        //对数据解密
        String decrypt = RSAEncryptUtil.decrypt(encrypt, privatekey);
        System.out.println("解密还原后的数据是" + decrypt);
    }

    //添加用户
    @Test
    public void ylhAddUserTest() throws Exception {
        //添加用户
        MailUser user = new MailUser("杨利华", "男", "2020-01-02", "2235662296@qq.com", "15285360367", "123456", "北京");
        System.out.println(user);
        mailUserService.addMailUser(user);
    }

    //删除用户并删除记录
    @Test
    public void ylhDeleteUserTest() {
        //根据id删除
        mailUserService.deleteMailUser(3);
    }

    //修改用户并删除记录
    @Test
    public void ylhUpdateUserTest() throws Exception {

        MailUser user = new MailUser(6, "杨利华", "女", "2020-01-02", "2235662296@qq.com", "15285360367", "123456", "北京");
        //修改用户(修改id相同的用户)
        mailUserService.updateMailUser(user);
    }

    //查询用户
    @Test
    public void ylhQueryUser() throws Exception {
        //根据id查询
        System.out.println(
                "=================" + mailUserService.queryMailUser(2)
        );
    }

    /**
     * 邮件发送测试
     */
     //获取D盘下text文件中的公钥和私钥
    @Test
    public void getKey(){
        Map<Integer, String> map = EncodeUtil.readKey();
        String publicKey = map.get(1);
        System.out.println("公钥是"+publicKey);
        String privateKey=map.get(2);
        System.out.println("私钥是"+privateKey);
    }
}
