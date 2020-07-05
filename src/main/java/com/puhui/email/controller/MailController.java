package com.puhui.email.controller;


import com.puhui.email.service.MailService;
import com.puhui.email.util.BaseResult;
import com.puhui.email.util.CommonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: 邹玉玺
 * @date: 2020/7/5-15:54
 */
@RestController
@Slf4j
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送简单邮件接口
     *
     * @param to      目标地址
     * @param topic   邮件主题
     * @param content 邮件内容
     */
    @ApiOperation ("普通邮件发送接口")
    @ApiImplicitParams ({@ApiImplicitParam (name = "to", value = "目标人物", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam (name = "topic", value = "邮件主题", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam (name = "content", value = "邮件内容", required = true, dataType = "String", paramType = "query")})
    @GetMapping ("/mail/sendMail")
    public BaseResult sendSimpleMail(String to, String topic, String content) {
        try {
            //获取一个随机的标识码，用于标记该用户邮箱
            String code = CommonUtil.getRandomNum();
            String redisCode = redisTemplate.opsForValue().get(to);
            //邮箱号对应缓存的标识符存在的话，不能发送邮件
            if (redisCode != null) {
                BaseResult result = new BaseResult("1", "30分钟内只能发送一次");
                return result;
            }
            System.out.println(code);
            //30分钟内只能发送一次
            redisTemplate.opsForValue().set(to,code,30, TimeUnit.MINUTES);

            mailService.sendSimpleMail(to, topic, content);
            BaseResult result = new BaseResult("0", "发送成功");
            return result;
        } catch (Exception e) {
            BaseResult result = new BaseResult("1", "发送失败");
            return result;
        }


    }
}