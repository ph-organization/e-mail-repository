package com.puhui.email.service.serviceImpl;


import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailUserService;

import com.puhui.email.util.EncodeUtil;
import com.puhui.email.util.RSAEncryptUtil;
import com.puhui.email.util.UserEncryptUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

/**
 * @description: 邮件用户crud实现
 * @author: 杨利华
 * @date: 2020/7/5
 */
@Service
@Transactional
public class MailUserServiceImpl implements MailUserService {

    //    //公钥加密
//    @Value("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHH1oreEIgmlTlKpJJt/gA1FiX6DNbcpHtdeQEvdTHVRUGnZuVxMdNPXG9LiHOGrSjHBjjg4YGfa+Q0nZAnUm1zGul5xTDBrawA+9Qrcp44Hd/6KpHAUq7rmZUGS8TSGwlHF6qyL4x5IH/py85FPSLGbYAOdt6Th3lAwZCXAx0aQIDAQAB")
//    public String publickey;
//
//    //私钥解密
//    @Value("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIcfWit4QiCaVOUqkkm3+ADUWJfoM1tyke115AS91MdVFQadm5XEx009cb0uIc4atKMcGOODhgZ9r5DSdkCdSbXMa6XnFMMGtrAD71Ctynjgd3/oqkcBSruuZlQZLxNIbCUcXqrIvjHkgf+nLzkU9IsZtgA523pOHeUDBkJcDHRpAgMBAAECgYB1SknDIgiEtHKs2l9RjlfAoJKmifDKmJEDRyo+02k3/iraK0U6pC08ZvGr/bdqaNQUIfeYxjo4uDEFzSrIu7+Wwgr3HCbHfEV7DIOe5LRDH1bUzUoyKIMKSoMA1A7PaDTSAx/0Xh3D+gNzGqN0tveEueB0ur5zfYY5AbXHa/loAQJBANK1b51MGRVLVf3M18K/eTzzGupQggsNNrFTBM/qzva276ybiOUIpz8AsQxEEETj6N+SygRF8wWNdgy15bQHCWECQQCkKqsvnvV1YAO4vUugwnVO8WvsUaK/yLiWoUfpCe4sm7XZz6B55NY/qX8ke7RgQ6E/sb6Hb2A0d9oHNCHlQSAJAkB44BSzi+An0xv1iPmNgwIt8NhT6vNvG5lwiEuOawlnhvJfdqpFmX04K1Fl0/XxTz1cZHz3jpknakt6Zy7q486BAkEAj/RKgEunefFj0g9LzgA21a6lsGg1im78TjnG0PbAP6Wa5RBH7BtaNCDxOJCxLuie8Tdvl1t2xQuDyGVSg7GD4QJBAJDCgTqBZxEeNpyYV/Fwdalqxuqzgup9H1sl6ORBMtnq9ljkePD2GwE+X1NUhIrdeyMr/5r1eHVc0ySm4d/3KoE=")
//    public String privatekey;
    private static Map<Integer, String> map = EncodeUtil.readKey();
    @Autowired
    MailUserMapper mailUserMapper;
    //过去公钥
    private String publickey = map.get(1);
    //获取私钥
    private String privatekey = map.get(2);

    @PersistenceContext
    private EntityManager manager;

    //添加用户
    @Override
  //  @CachePut (value = "mailUser", key = "#mailUser.name.toString()")
    public void addMailUser(MailUser mailUser) throws Exception {
        //创建对象用于加密保存数据/解密修改数据
        MailUser user = new MailUser();
        user = UserEncryptUtil.encrytUser(mailUser);
        mailUserMapper.save(user);

    }

    //修改用户
    @Override
    @CachePut (value = "mailUser", key = "#mailUser.id.toString()")
    public void updateMailUser(MailUser mailUser) throws Exception {
        //创建对象用于加密保存数据/解密修改数据
        MailUser user = new MailUser();
        user = UserEncryptUtil.encrytUser(mailUser);
        mailUserMapper.saveAndFlush(user);
    }

    //查询用户
    @Override
    @Cacheable (value = "mailUser", key = "#id.toString()")
    public MailUser queryMailUser(Integer id) throws Exception {
        Session session = manager.unwrap(Session.class);
        MailUser mailUser = mailUserMapper.getOne(id);
        MailUser user = UserEncryptUtil.decryptUser(mailUser);
        session.evict(user);
        return user;
    }

    //修改多个用户
    @Override
    public void updateMailUsers(List<MailUser> list) throws Exception {
        for (MailUser mu : list) {
            updateMailUser(mu);
        }
    }
    //删除用户
    @Override
    public void deleteMailUser(Integer id) {
        mailUserMapper.deleteById(id);

    }

    //删除多个用户
    @Override
    public void deleteMailUsers(List<Integer> list) {
        System.out.println("=====================" + list);
        for (int i : list) {
            deleteMailUser(i);
        }
    }



    @Override
    @CachePut (value = "mailuser", key = "#mailUser.id.toString()")
    public void addCache(MailUser mailUser) {
        mailUserMapper.saveAndFlush(mailUser);
    }

    //查询全部用户
    @Override
    public List<MailUser> queryAllMailUser() {
        return mailUserMapper.findAll();
    }

}
