package com.kyj.fmk.model.kafka.consume;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행 소모를 위한 DTO(배치 강제세션종료)
 */
@Getter
@Setter
public class ConsumeBatchWsDisconnectDTO {
    private String usrSeqId;

}
