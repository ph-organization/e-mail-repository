package com.puhui.email.service.serviceImpl;


import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//
//    //用于封装随机产生的公钥与私钥
//    Map<Integer, String> keyMap = new HashMap<Integer, String>();

    //公钥加密
    @Value("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHH1oreEIgmlTlKpJJt/gA1FiX6DNbcpHtdeQEvdTHVRUGnZuVxMdNPXG9LiHOGrSjHBjjg4YGfa+Q0nZAnUm1zGul5xTDBrawA+9Qrcp44Hd/6KpHAUq7rmZUGS8TSGwlHF6qyL4x5IH/py85FPSLGbYAOdt6Th3lAwZCXAx0aQIDAQAB")
    public String publickey;

    //私钥解密
    @Value("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIcfWit4QiCaVOUqkkm3+ADUWJfoM1tyke115AS91MdVFQadm5XEx009cb0uIc4atKMcGOODhgZ9r5DSdkCdSbXMa6XnFMMGtrAD71Ctynjgd3/oqkcBSruuZlQZLxNIbCUcXqrIvjHkgf+nLzkU9IsZtgA523pOHeUDBkJcDHRpAgMBAAECgYB1SknDIgiEtHKs2l9RjlfAoJKmifDKmJEDRyo+02k3/iraK0U6pC08ZvGr/bdqaNQUIfeYxjo4uDEFzSrIu7+Wwgr3HCbHfEV7DIOe5LRDH1bUzUoyKIMKSoMA1A7PaDTSAx/0Xh3D+gNzGqN0tveEueB0ur5zfYY5AbXHa/loAQJBANK1b51MGRVLVf3M18K/eTzzGupQggsNNrFTBM/qzva276ybiOUIpz8AsQxEEETj6N+SygRF8wWNdgy15bQHCWECQQCkKqsvnvV1YAO4vUugwnVO8WvsUaK/yLiWoUfpCe4sm7XZz6B55NY/qX8ke7RgQ6E/sb6Hb2A0d9oHNCHlQSAJAkB44BSzi+An0xv1iPmNgwIt8NhT6vNvG5lwiEuOawlnhvJfdqpFmX04K1Fl0/XxTz1cZHz3jpknakt6Zy7q486BAkEAj/RKgEunefFj0g9LzgA21a6lsGg1im78TjnG0PbAP6Wa5RBH7BtaNCDxOJCxLuie8Tdvl1t2xQuDyGVSg7GD4QJBAJDCgTqBZxEeNpyYV/Fwdalqxuqzgup9H1sl6ORBMtnq9ljkePD2GwE+X1NUhIrdeyMr/5r1eHVc0ySm4d/3KoE=")
    public String privatekey;

    //添加用户
    @Override
    @CachePut(value = "mailuser",key = "#mailUser.id.toString()")
    public void addMailUser(MailUser mailUser) throws Exception{


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
