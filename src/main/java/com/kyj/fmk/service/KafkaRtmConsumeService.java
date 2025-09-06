package com.kyj.fmk.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 * 2025-08-28
 * @author 김용준
 * RealTime서비스에서 카프카 토픽에 대한 이벤트를 소모하는 서비스
 */
public interface KafkaRtmConsumeService {

    public void consumePushLiveUser(ConsumerRecord<String, String> record, Acknowledgment ack);
    public void consumeMemberLogout(ConsumerRecord<String, String> record, Acknowledgment ack);
    public void consumeBatchWsDisconnect(ConsumerRecord<String, String> record, Acknowledgment ack);
    public void consumeWthrUpd(ConsumerRecord<String, String> record, Acknowledgment ack);
    public void consumeBtlLtrFlowRe(ConsumerRecord<String, String> record, Acknowledgment ack);
    public void consumeBtlLtrRpy(ConsumerRecord<String, String> record, Acknowledgment ack);

}
