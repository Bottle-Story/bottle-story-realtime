package com.kyj.fmk.model.ws;

import java.security.Principal;
/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓세션 에서 사용되는 커스텀 Principal
 */
public class StompPrincipal implements Principal {
    private final String usrSeqId; // 유저 고유 아이디

    public StompPrincipal(String usrSeqId){
        this.usrSeqId = usrSeqId;
    }
    @Override
    public String getName() {
        return this.usrSeqId;
    }
}
