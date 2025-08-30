package com.kyj.fmk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.model.kafka.KafkaPushLiveUserDTO;
import com.kyj.fmk.model.kafka.consume.ConsumeBatchWsDisconnectDTO;
import com.kyj.fmk.model.kafka.consume.ConsumeLogoutDTO;
import com.kyj.fmk.model.ws.WsLiveUserDTO;
import com.kyj.fmk.model.ws.WsRes;
import com.kyj.fmk.model.ws.WsTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

/**
 * 2025-08-28
 * @author 김용준
 * RealTime서비스에서 카프카 토픽에 대한 이벤트를 소모하는 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaRtmConsumeServiceImpl implements KafkaRtmConsumeService{

      private final SimpMessagingTemplate messagingTemplate;
      private final RedisTemplate<String,Object> redisTemplate;
      private final WebSocketSessionService webSocketSessionService;
      private final ObjectMapper objectMapper;
      private final KafkaRtmPublishService kafkaRtmPublishService;


    /**
     *  회원 로그아웃에 대한 이벤트 소모(레디스 세션 제거, 웹소켓 세션 제거 , 라이브유저수 푸시)
     * @param record
     * @param ack
     */
    @Override
    @KafkaListener(
            topics = KafkaTopic.MEMBER_LOGOUT,
            groupId = "#{ 'realtime.consume.' + T(com.kyj.fmk.core.model.KafkaTopic).MEMBER_LOGOUT +  T(java.util.UUID).randomUUID().toString()}"
    )
    public void consumeMemberLogout(ConsumerRecord<String, String> record, Acknowledgment ack) {

        try {
            String json =record.value();
            ConsumeLogoutDTO consumeLogoutDTO= objectMapper.readValue(json,ConsumeLogoutDTO.class);

            webSocketSessionService.disconnect(consumeLogoutDTO.getUsrSeqId());


            KafkaPushLiveUserDTO kafkaPushLiveUserDTO = new KafkaPushLiveUserDTO();

            kafkaRtmPublishService.publishPushLiveUser(kafkaPushLiveUserDTO);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

    }
    /**
     * 배치에 의해 웹소켓 강제종료 회원을 수신 (매 5분)
     * @param record
     * @param ack
     */
    @KafkaListener(
            topics = KafkaTopic.BATCH_WS_DISCONNECT,
            groupId = "#{ 'realtime.consume.' + T(com.kyj.fmk.core.model.KafkaTopic).BATCH_WS_DISCONNECT +  T(java.util.UUID).randomUUID().toString()}"
    )
    @Override
    public void consumeBatchWsDisconnect(ConsumerRecord<String, String> record, Acknowledgment ack) {

        try {
            String json =record.value();
            ConsumeBatchWsDisconnectDTO batchWsDisconnectDTO= objectMapper.readValue(json, ConsumeBatchWsDisconnectDTO.class);
log.info("dd={}",record);
            webSocketSessionService.disconnect(batchWsDisconnectDTO.getUsrSeqId());

            KafkaPushLiveUserDTO kafkaPushLiveUserDTO = new KafkaPushLiveUserDTO();

            kafkaRtmPublishService.publishPushLiveUser(kafkaPushLiveUserDTO);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }
    }

    /**
     * 실시간 접속자 수 전체 푸시를 위한 이벤트 발행에 대한 소모
     * @param record
     * @param ack
     */
    @Override
    @KafkaListener(
            topics = KafkaTopic.REALTIME_PUSH_LIVE_USER,
            groupId = "#{ 'realtime.consume.' + T(com.kyj.fmk.core.model.KafkaTopic).REALTIME_PUSH_LIVE_USER }"
    )
    public void consumePushLiveUser(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            // 실시간 유저 수
            Long value = redisTemplate.opsForZSet().zCard(RedisKey.WS_SESSION_Z_SET_KEY);

            if(value == null){
                throw new KyjBizException(CmErrCode.CM020);
            }

            
            BigInteger userCnt = BigInteger.valueOf(value);

            //웹소켓 전송 데이터
            WsLiveUserDTO wsLiveUserDTO = new WsLiveUserDTO();
            wsLiveUserDTO.setUserCnt(userCnt);

            WsRes<WsLiveUserDTO> wsRes = new WsRes<>(WsTopic.TOPIC_LIVE_USER,wsLiveUserDTO);

            // 웹소켓 푸시
            messagingTemplate.convertAndSend(wsRes.getWsTopic().getTopic(),wsRes);

            // 성공 시 수동 커밋 호출
            ack.acknowledge();
        } catch (Exception e) {
            // 에러 처리 (커밋 안 함, 재시도 가능)
            throw new KyjBizException(CmErrCode.CM020);

        }
    }

}
