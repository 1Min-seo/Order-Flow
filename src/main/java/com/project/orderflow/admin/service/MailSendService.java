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

    public String makeRandomNum() {
        Random rand = new Random();
        StringBuilder randomNum = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomNum.append(rand.nextInt(10));
        }
        authNumber = randomNum.toString();
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
    }

    public boolean checkAuthNum(String email, String authNum) {
        String storedEmail = redisUtil.getData(authNum);
        if(storedEmail!=null && storedEmail.equals(email)){
            return true;
        }else{
            return false;
        }
    }

    public String joinEmail(String email) {
        makeRandomNum();
        String setFrom = "readex11@naver.com";
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "ORDER FLOW에 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + " 입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요.";

        mailSend(setFrom, toMail, title, content);
        redisUtil.setDataExpire(authNumber, toMail, 60 * 5L);
        return authNumber;
    }

}
