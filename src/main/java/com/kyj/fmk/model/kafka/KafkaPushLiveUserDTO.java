package com.kyj.fmk.model.kafka;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.BaseKafkaDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행을 위한 DTO(실시간 사용자 수 푸시)
 */
@Getter
@Setter
public class KafkaPushLiveUserDTO extends BaseKafkaDTO {

    public KafkaPushLiveUserDTO(){
        super.setTopic(KafkaTopic.REALTIME_PUSH_LIVE_USER);
        super.setFrom("REAL_TIME_SERVICE");
    }
}
