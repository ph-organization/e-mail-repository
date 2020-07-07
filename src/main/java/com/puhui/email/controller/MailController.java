package com.puhui.email.controller;


import com.puhui.email.entity.MailUser;
import com.puhui.email.service.MailService;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.BaseResult;
import com.puhui.email.util.CommonUtil;
import com.puhui.email.util.EncodeUtil;
import com.puhui.email.util.RSAEncryptUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:54
 */
@RestController
@Slf4j
public class MailController {
    private static Map<Integer, String> map = EncodeUtil.readKey();
    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private MailUserService userService;
    //配置文件中的公钥
//    @Value ("${rsa.publicKey}")
    private String publicKey=map.get(1);
    //配置文件中的私钥
  //  @Value ("${rsa.privateKey}")
    private String privateKey=map.get(2);

    /**
     * 发送简单邮件接口
     *
     * @param target  目标地址
     * @param topic   邮件主题
     * @param content 邮件内容
     */
    @ApiOperation ("普通邮件发送接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "target", value = "目标用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "topic", value = "邮件主题(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "邮件内容(不填，发邮件时获取)", required = false, dataType = "String", paramType = "query"),
    })
    @GetMapping ("/mail/sendMail")
    public BaseResult sendSimpleMail(String target, String topic, String content) throws Exception {
        //创建一个封装结果的结果集
        BaseResult result = new BaseResult("", "");
        //查询出数据库所有用户
        List<MailUser> mailUsers = userService.queryAllMailUser();

        for (MailUser user : mailUsers) {

            if ((RSAEncryptUtil.decrypt(user.getName(), privateKey)).toString().equals(target)) {
                //如果解密后的用户名与需要发送的用户名相同，获取该用户邮箱,并对其进行解密

                //对邮箱解密获取原邮箱
                String email = (RSAEncryptUtil.decrypt(user.getEmail(), privateKey)).toString();
                try {
                    //获取一个随机的标识码，用于标记该用户邮箱
                    String code = CommonUtil.getRandomNum();
                    String redisCode = redisTemplate.opsForValue().get(target);
                    //邮箱号对应缓存的标识符存在的话，不能发送邮件
                    if (redisCode != null) {
                        result.setCode("1");
                        result.setMessage("30分钟内只能发送一次");
                        return result;
                    }
                    //缓存中没有标识符   可以发送邮件
                    mailService.sendSimpleMail(email, topic, content, user);
                    result.setCode("0");
                    result.setMessage("发送成功");
                    // 发送成功后将标识码存入redis 30分钟内只能发送一次
                    redisTemplate.opsForValue().set(target, code, 30, TimeUnit.MINUTES);
                    return result;
                } catch (Exception e) {
                    result.setCode("1");
                    result.setMessage("发送失败");
                    return result;
                }
            }
        }
        //用户不存在
        result.setCode("1");
        result.setMessage("该用户不存在，请核对用户名是否正确");
        return result;
    }
}
