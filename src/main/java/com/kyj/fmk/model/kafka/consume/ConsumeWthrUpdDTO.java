package com.kyj.fmk.model.kafka.consume;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 2025-08-28
 * @author 김용준
 * 카프카 이벤트발행 소모를 위한 DTO(날씨업데이트)
 */
@Getter
@Setter
public class ConsumeWthrUpdDTO {

    private LocalDate wthrDate;
    private LocalTime wthrTime;
    private LocalDate wthrBaseDate;
    private LocalTime wthrBaseTime;
    private LocalTime sunSetTime;
    private LocalTime sunRiseTime;
    private String oceanCode;
    private String skyCode;
    private String particleCode;
    private String t1h;
    private String usrSeqId;
}
