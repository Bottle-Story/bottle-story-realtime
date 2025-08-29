package com.kyj.fmk.model.kafka;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.BaseKafkaDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행을 위한 DTO(회원 위치정보)
 */
@Getter
@Setter
public class KafkaMemberLocationDTO extends BaseKafkaDTO {

    private String usrSeqId;
    private double lat;
    private double lot;

    public KafkaMemberLocationDTO(){
        super.setTopic(KafkaTopic.REALTIME_MEMBER_LOCATION);
        super.setFrom("REAL_TIME_SERVICE");
    }
}
