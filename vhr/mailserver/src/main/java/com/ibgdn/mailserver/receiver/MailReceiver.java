package com.ibgdn.mailserver.receiver;

import com.ibgdn.vhr.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailReceiver {

    public static final Logger log = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.server")
    public void handler(Employee employee) {
        // 收到 rabbitmq 的消息，将会发送邮件给员工
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            // 邮件发件箱地址
            helper.setFrom(mailProperties.getUsername());
            // 邮件收件箱地址
            helper.setTo(employee.getEmail());
            // 邮件主题
            helper.setSubject("欢迎邮件！");
            // 邮件发送日期
            helper.setSentDate(new Date());

            // 配置模板页面数据
            Context context = new Context();
            context.setVariable("niceName", employee.getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            context.setVariable("jobLevelName", employee.getJobLevel().getName());
            context.setVariable("positionName", employee.getPosition().getName());

            // 获取模板的渲染数据
            String mailStr = templateEngine.process("mail", context);
            // 设置邮件内容
            helper.setText(mailStr, true);
            // 发送邮件
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("邮件发送失败，错误信息：[{}]", e.getMessage());
        }
    }
}
