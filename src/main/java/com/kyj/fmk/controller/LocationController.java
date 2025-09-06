package com.kyj.fmk.controller;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.model.kafka.KafkaMemberLocationDTO;
import com.kyj.fmk.model.ws.ReqWsLocationDTO;
import com.kyj.fmk.service.KafkaRtmPublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 위치정보 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class LocationController {

    private final KafkaRtmPublishService kafkaRtmPublishService;
    /**
     * 실시간 웹소켓으로 부터 변하는 위치 좌표를 받아서 이벤트를 발행하는 컨트롤러
     * @param reqWsLocationDTO
     */
    @MessageMapping("/member/location")
    public void handleLocation(ReqWsLocationDTO reqWsLocationDTO, SimpMessageHeaderAccessor headerAccessor) {
        String usrSeqId = (String) headerAccessor.getSessionAttributes().get("usrSeqId");

        KafkaMemberLocationDTO kafkaMemberLocationDTO = new KafkaMemberLocationDTO();
        kafkaMemberLocationDTO.setUsrSeqId(usrSeqId);
        kafkaMemberLocationDTO.setLat(Double.parseDouble(reqWsLocationDTO.getLat()));
        kafkaMemberLocationDTO.setLot(Double.parseDouble(reqWsLocationDTO.getLot()));

        kafkaRtmPublishService.publishMemberLocation(kafkaMemberLocationDTO);
    }
}
