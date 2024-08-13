package com.project.orderflow.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailSendService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtil redisUtil;

    private String authNumber;

    public boolean CheckAuthNum(String email, String authNum) {
        if (redisUtil.getData(authNum) == null) {
            return false;
        } else if (redisUtil.getData(authNum).equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    public String makeRandomNum() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 6; i++) {
            randomNum += Integer.toString(rand.nextInt(10));
        }
        authNumber =randomNum;
        return authNumber;
    }

    public String joinEmail(String email) {
        makeRandomNum();
        String setFrom = "readex11@naver.com";
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "ORDER FLOW에 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요.";
        mailSend(setFrom, toMail, title, content);
        return authNumber;
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisUtil.setDataExpire(authNumber, toMail, 60 * 5L);
    }
}
