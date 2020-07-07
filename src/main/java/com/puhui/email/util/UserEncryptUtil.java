package com.puhui.email.util;

import com.puhui.email.entity.MailUser;

import java.util.Map;

/**
 * @author: 邹玉玺
 * @date: 2020/7/7-14:37
 *
 * 用户加密解密   返回一个用户
 */
public class UserEncryptUtil {
    private static Map<Integer, String> map = EncodeUtil.readKey();
    //过去公钥
    private static String publickey = map.get(1);
    //获取私钥
    private static String privatekey = map.get(2);
    /**
    封装对象加密工具
     */
    public static MailUser encrytUser(MailUser user) throws Exception {
        user.setId(user.getId());
        //加密
        user.setName(RSAEncryptUtil.encrypt(user.getName(), publickey));
        user.setSex(user.getSex());
        user.setEmail(RSAEncryptUtil.encrypt(user.getEmail(), publickey));
        user.setPwd(RSAEncryptUtil.encrypt(user.getPwd(), publickey));
        user.setBirthday(user.getBirthday());
        user.setPhone(RSAEncryptUtil.encrypt(user.getPhone(), publickey));
        user.setResult(user.getResult());
        user.setAddress(RSAEncryptUtil.encrypt(user.getAddress(), publickey));
        return  user;
    }

    /**
     * 解密
     *
     */
    public static MailUser decryptUser(MailUser user) throws Exception {
        user.setEmail(RSAEncryptUtil.decrypt(user.getEmail(),privatekey));
        user.setName(RSAEncryptUtil.decrypt(user.getName(),privatekey));
        user.setAddress(RSAEncryptUtil.decrypt(user.getAddress(),privatekey));
        user.setPhone(RSAEncryptUtil.decrypt(user.getPhone(),privatekey));
        user.setPwd(RSAEncryptUtil.decrypt(user.getPwd(),privatekey));
        return user;
    }

}
