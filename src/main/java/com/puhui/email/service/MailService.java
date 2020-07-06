package com.puhui.email.service;

import com.puhui.email.entity.MailUser;

import java.io.File;

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:25
 */
public interface MailService {
    /**
     *发送文本邮件
     * @param to       收件人
     * @param topic    主题
     * @param content    内容
     */
    public void sendSimpleMail(String to, String topic, String content, MailUser user) throws Exception;

    /**
     * 发送带附件的邮件
     * @param to  收件人
     * @param topic  邮件主题
     * @param content  邮件内容
     * @param file   附件
     */
    public void sendFileMail(String to, String topic, String content, File file);

}
