package com.jack.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbTaskApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage maill = new SimpleMailMessage();
        maill.setSubject("Subject...");
        maill.setText("content....");
        maill.setTo("18829346477@163.com");
        maill.setFrom("243853974@qq.com");

        mailSender.send(maill);
    }

    @Test
    public void sendMimeMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setSubject("Subject...");
        messageHelper.setText("<b style='color:red'>content....</b>", true);
        messageHelper.setTo("18829346477@163.com");
        messageHelper.setFrom("243853974@qq.com");

        messageHelper.addAttachment("奢侈态度",  new File("/Users/dreamli/Downloads/奢侈态度.txt"));

        mailSender.send(mimeMessage);
    }

}
