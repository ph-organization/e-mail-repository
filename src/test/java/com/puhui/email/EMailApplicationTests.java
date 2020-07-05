package com.puhui.email;



import com.puhui.email.util.RSAEncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EMailApplicationTests {


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

}
