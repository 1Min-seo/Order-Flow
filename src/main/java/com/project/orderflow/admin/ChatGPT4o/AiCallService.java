package com.project.orderflow.admin.ChatGPT4o;

import java.io.IOException;
import java.util.List;

import com.project.orderflow.admin.domain.orderlist;
import com.project.orderflow.admin.repository.OrderlistRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AiCallService {
    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final OrderlistRepository orderlistRepository;
    

    private final RestTemplate template;

    public ChatGPTResponse requestTextAnalysis(String requestText) {
        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, 500, "user", requestText);
        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
    }

    public ChatGPTResponse requestImageAnalysis(MultipartFile image, String requestText) throws IOException {
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
    }

    public String analyzeOrdersByTableId(Integer tableorderid) {
        // 테이블 ID로 orderlist 정보 조회
        List<orderlist> orders = orderlistRepository.findByTableorderid(tableorderid);

        if (orders.isEmpty()) {
            return "No orders found for this table.";
        }

        // AI 분석 요청을 위한 텍스트 생성 (예시: 주문 항목 요약)
        StringBuilder orderSummary = new StringBuilder();
        for (orderlist order : orders) {
            orderSummary.append("Menu: ").append(order.getMenu())
                    .append(", Quantity: ").append(order.getQuality())
                    .append(", Date: ").append(order.getDate())
                    .append("\n");
        }

        // AI 분석 요청
        ChatGPTResponse response = requestTextAnalysis(orderSummary.toString()+"가 주문내역인데 주문내역을 분석해서 알려줘.");
        return response.getChoices().get(0).getMessage().getContent();
    }
}