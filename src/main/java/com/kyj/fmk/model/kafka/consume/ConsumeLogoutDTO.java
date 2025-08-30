package com.kyj.fmk.model.kafka.consume;

import lombok.Getter;
import lombok.Setter;
/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행 소모를 위한 DTO(로그아웃)
 */
@Getter
@Setter
public class ConsumeLogoutDTO {
    private String usrSeqId;

}
