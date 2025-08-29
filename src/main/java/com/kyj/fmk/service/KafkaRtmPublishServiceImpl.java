package com.kyj.fmk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.model.kafka.KafkaMemberLocationDTO;
import com.kyj.fmk.model.kafka.KafkaPushLiveUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 2025-08-28
 * @author 김용준
 * RealTime서비스에서 카프카 토픽에 대한 이벤트를 발행하는 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class KafkaRtmPublishServiceImpl implements KafkaRtmPublishService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     *  실시간 접속자 수 전체 푸시를 위한 이벤트 발행
     * @param rtmWsConnectKafkaDTO
     */
    @Override
    public void publishPushLiveUser(KafkaPushLiveUserDTO rtmWsConnectKafkaDTO) {


        String data  = null;

        try {

         data = objectMapper.writeValueAsString(rtmWsConnectKafkaDTO);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

        if(data == null){
            throw new KyjBizException(CmErrCode.CM019);

        }

        kafkaTemplate.send(rtmWsConnectKafkaDTO.getTopic(),data);
    }

    /**
     * 회원위치정보 이벤트 발생
     * @param kafkaMemberLocationDTO
     */
    @Override
    public void publishMemberLocation(KafkaMemberLocationDTO kafkaMemberLocationDTO) {

        String data  = null;

        try {

            data = objectMapper.writeValueAsString(kafkaMemberLocationDTO);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

        if(data == null){
            throw new KyjBizException(CmErrCode.CM019);

        }

        kafkaTemplate.send(kafkaMemberLocationDTO.getTopic(),data);
    }
}
