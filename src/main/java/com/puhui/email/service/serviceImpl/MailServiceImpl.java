package com.puhui.email.service.serviceImpl;


import com.puhui.email.entity.MailUser;
import com.puhui.email.mapper.MailUserMapper;
import com.puhui.email.service.MailService;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.EncodeUtil;
import com.puhui.email.util.UserEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:26
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    private static Map<Integer, String> map = EncodeUtil.readKey();
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailUserService userService;
    @Autowired
    private MailUserMapper userMapper;
//    //配置文件中的公钥
//    @Value ("${rsa.publicKey}")
//    private String publicKey;
//    //配置文件中的私钥
//    @Value ("${rsa.privateKey}")
//    private String privateKey;
    // 配置文件中的发送者
    @Value ("${mail.fromMail.sender}")
    private String sender;
    //过去公钥
    private String publicKey = map.get(1);
    //获取私钥
    private String privateKey = map.get(2);

    @Override
    public void sendSimpleMail(String email, String topic, String content, MailUser user) throws Exception {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件发送人
        message.setFrom(sender);
        //设置邮件主题
        message.setSubject(topic);
        //设置邮件内容
        message.setText(content);

        //邮件收件人
        message.setTo(email);
        //设置邮件内容


        //用户加密信息解密
        MailUser mailUser = UserEncryptUtil.decryptUser(user);
        try {
            user.setContent(content);
            //设置邮件主题
            user.setTopic(topic);
            //发送邮件
            mailSender.send(message);
            //发送成功后 ,设置发送结果为true
            user.setResult("success");
            System.out.println(user);
            userService.updateMailUser(mailUser);
        } catch (Exception e) {
            user.setResult("failure");
            //保存用户
            userService.updateMailUser(mailUser);

            e.printStackTrace();
        }


    }

    @Override
    public void sendFileMail(String to, String topic, String content, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(topic);
            helper.setText(content);

            helper.addAttachment(file.getName(), file);
            //发送邮件
            mailSender.send(message);
            log.info("邮件发送成功。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

}
