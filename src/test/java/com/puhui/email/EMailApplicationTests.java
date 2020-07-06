package com.puhui.email;



import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailService;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.RSAEncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EMailApplicationTests {
    @Autowired
    private MailUserMapper mailUserMapper;
    //配置文件中的公钥
    @Value ("${rsa.publicKey}")
    private String publicKey;
    //配置文件中的私钥
    @Value("${rsa.privateKey}")
    private String privateKey;

    /**
     * 邹玉玺
     * 测试加密解密
     */
    @Test
  public void contextLoads() throws Exception {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
        //邮箱
        String email="1003941268@qq.com";
        //生成公钥私钥
        RSAEncryptUtil.genKeyPair(keyMap);
        //公钥
        String publickey = keyMap.get(0);
        //私钥
        String privatekey = keyMap.get(1);
        //对邮箱进行加密
        String encrypt = RSAEncryptUtil.encrypt(email, publickey);
        System.out.println("加密后的数据"+encrypt);
        //对数据解密
        String decrypt = RSAEncryptUtil.decrypt(encrypt, privatekey);
        System.out.println("解密还原后的数据是"+decrypt);
    }

    /**
     *  邮件用户的crud，redis记录测试
     *  杨利华
     */

    @Autowired
    MailUserService mailUserService;

    //添加用户
    @Test
    public void  ylhAddUserTest() throws Exception {
        //添加用户
        mailUserService.addMailUser(new MailUser(1,"杨利华","男","2020-01-02","2235662296@qq.com","15285360367","123456","北京",false));
    }
    //删除用户并删除记录
    @Test
    public void ylhDeleteUserTest(){
        //根据id删除
        mailUserService.deleteMailUser(3);
    }
    //修改用户并删除记录
    @Test
    public void ylhUpdateUserTest() throws Exception {

        MailUser user=new MailUser(2,"杨利华","女","2020-01-02","2235662296@qq.com","15285360367","123456","北京",false);
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
     * 根据姓名查询用户
     */
    @Test
    public void getByName() throws Exception {
        //查询所有
        List<MailUser> mailUsers = mailUserService.queryAllMailUser();
        for (MailUser user:mailUsers) {
            if (RSAEncryptUtil.decrypt(user.getName(), privateKey).toString().equals("zyx")){
                System.out.println(user);
            }
        }

    }
}
