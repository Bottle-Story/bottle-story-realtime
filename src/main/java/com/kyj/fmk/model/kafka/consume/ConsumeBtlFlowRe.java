package com.kyj.fmk.model.kafka.consume;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행 소모를 위한 DTO(유리병 작성 )
 */
@Getter
@Setter
public class ConsumeBtlFlowRe {
    List<String> btlLtrNoList;
    private String usrSeqId;
}
