package com.kyj.fmk.model.ws;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓에서 실시간 사용자수를 전송하는 객체
 */
@Getter
@Setter
public class WsLiveUserDTO {

    public BigInteger userCnt;
}
