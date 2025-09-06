package com.kyj.fmk.model.ws;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 통신으로부터 위치정보를 받는 DTO
 */
@Getter
@Setter
public class ReqWsLocationDTO {

    private String lat;
    private String lot;
}
