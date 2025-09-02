package com.kyj.fmk.model.kafka.consume;

import lombok.Getter;
import lombok.Setter;
/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행 소모를 위한 DTO(유리병 댓글 )
 */
@Getter
@Setter
public class ConsumeBtlRpyDTO {
    private String btlLtrNo;
    private char [] contentArr;
    private String usrSeqId;
}
