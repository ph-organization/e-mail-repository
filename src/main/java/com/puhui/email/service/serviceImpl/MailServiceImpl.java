package com.puhui.email.service.serviceImpl;


import com.puhui.email.service.MailService;
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

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:26
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    // 配置文件中的发送者
    @Value ("${mail.fromMail.sender}")
    private String sender;

    @Override
    public void sendSimpleMail(String to, String topic, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(sender);
        System.out.println("邮件发送人是"+sender);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(topic);
        //邮件内容
        message.setText(content);
        //发送邮件
        try {
            mailSender.send(message);
            log.info("邮件接收人"+to+"主题"+topic+"内容"+content+"邮件发送成功");
        }catch (Exception e){
            log.error("邮件接收人"+to+"主题"+topic+"内容"+content+"邮件发送出现异常");
            log.error("异常信息为"+e.getMessage());
            log.error("异常堆栈信息为-->");
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
