package com.kyj.fmk.service;

import com.kyj.fmk.model.kafka.KafkaMemberLocationDTO;
import com.kyj.fmk.model.kafka.KafkaPushLiveUserDTO;

/**
 * 2025-08-28
 * @author 김용준
 * RealTime서비스에서 카프카 토픽에 대한 이벤트를 발행하는 서비스
 */
public interface KafkaRtmPublishService {

    public void publishPushLiveUser(KafkaPushLiveUserDTO rtmWsConnectKafkaDTO);
    public void publishMemberLocation(KafkaMemberLocationDTO kafkaMemberLocationDTO);
}
