package com.kyj.fmk.model.ws;

import com.kyj.fmk.model.kafka.consume.ConsumeWthrUpdDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class WsWthrDTO {

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

    public WsWthrDTO(ConsumeWthrUpdDTO consumeWthrUpdDTO){
        this.wthrDate     = consumeWthrUpdDTO.getWthrDate();
        this.wthrTime     = consumeWthrUpdDTO.getWthrTime();
        this.wthrBaseDate = consumeWthrUpdDTO.getWthrBaseDate();
        this.wthrBaseTime = consumeWthrUpdDTO.getWthrBaseTime();
        this.sunSetTime   = consumeWthrUpdDTO.getSunSetTime();
        this.sunRiseTime  = consumeWthrUpdDTO.getSunRiseTime();
        this.oceanCode    = consumeWthrUpdDTO.getOceanCode();
        this.skyCode      = consumeWthrUpdDTO.getSkyCode();
        this.particleCode = consumeWthrUpdDTO.getParticleCode();
        this.t1h          = consumeWthrUpdDTO.getT1h();

    }

}
