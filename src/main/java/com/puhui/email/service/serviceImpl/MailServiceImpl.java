package com.puhui.email.service.serviceImpl;


import com.puhui.email.entity.MailUser;
import com.puhui.email.service.MailService;
import com.puhui.email.service.MailUserService;
import com.puhui.email.util.RSAEncryptUtil;
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
import java.util.List;

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:26
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailUserService userService;

    // 配置文件中的发送者
    @Value ("${mail.fromMail.sender}")
    private String sender;
    //配置文件中的公钥
    @Value ("${rsa.publicKey}")
    private String publicKey;
    //配置文件中的私钥
    @Value ("${rsa.privateKey}")
    private String privateKey;

    @Override
    public void sendSimpleMail(String to, String topic, String content) throws Exception {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件发送人
        message.setFrom(sender);
        //设置邮件主题
        message.setSubject(topic);
        //设置邮件内容
        message.setText(content);

        //查询出数据库所有用户
        List<MailUser> mailUsers = userService.queryAllMailUser();
        //遍历查询出需要发送邮件的目标用户
        for (MailUser user : mailUsers) {

            if (RSAEncryptUtil.decrypt(user.getName(), privateKey).toString().equals(to)) {
                //解密后的用户名与需要发送的用户名相同，获取该用户邮箱,并对其进行解密
                user.getEmail();
                String email = RSAEncryptUtil.encrypt(user.getEmail(), privateKey).toString();
                //邮件接收人
                message.setTo(email);

                try {
                    //发送邮件
                    mailSender.send(message);
                    log.info("邮件接收人" + to + "主题" + topic + "内容" + content + "邮件发送成功");
                    //发送成功后  将数据修改存入数据库
                    user.setEmail(email);
                    user.setResult(true);
                    userService.updateMailUser(user);
                } catch (Exception e) {
                    user.setResult(false);
                    userService.updateMailUser(user);
                    log.error("邮件接收人" + to + "主题" + topic + "内容" + content + "邮件发送出现异常");
                    log.error("异常信息为" + e.getMessage());
                    log.error("异常堆栈信息为-->");
                    e.printStackTrace();
                }
            }
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
