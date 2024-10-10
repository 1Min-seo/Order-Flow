package com.project.orderflow.admin.service;


import com.project.orderflow.admin.domain.Phone;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import java.util.Random;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class PhoneService {

    Random random = new Random();

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    @Autowired
    Phone phone;
    // 단일 메시지 발송 메서드
    public SingleMessageSentResponse sendOne(String to) {

        int createNum = 0;  			//1자리 난수
        String ranNum = ""; 			//1자리 난수 형변환 변수
        int letter    = 4;			//난수 자릿수:6
        String resultNum = "";  		//결과 난수

        for (int i=0; i<letter; i++) {

            createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
            ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
            resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
        }
        phone.setRand(resultNum);

        Message message = new Message();
        // 발신번호는 적절한 번호로 설정해야 합니다.
        message.setFrom("01076294088");
        message.setTo(to);
        message.setText("[ECO FLOW]\n"+ "인증번호 " + resultNum + "");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        System.out.println(response);

        return response;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {

        System.out.println(phone.getRand()+"이게 랜덤 번호임!!");
        if(phoneNumber.equals(phone.getRand())){
            System.out.println("같네");
            return true;
        }
        else {
            System.out.println("다름!");
            return false;
        }
        // 여기에 전화번호의 유효성을 확인하는 로직을 추가합니다.
        // 예를 들어, 정규 표현식을 사용하여 형식을 검사할 수 있습니다.
        // 유효한 전화번호인 경우 true를 반환하고, 그렇지 않으면 false를 반환합니다.

    }


}
