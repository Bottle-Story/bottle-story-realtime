package com.kyj.fmk.service;
/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 세션을 관리하는 서비스
 */
public interface WebSocketSessionService {

    public void disconnect(String usrSeqId);
}
