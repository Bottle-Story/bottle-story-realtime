package com.kyj.fmk.service;

import com.kyj.fmk.manager.StompSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketSessionServiceImpl implements WebSocketSessionService {

    private final StompSessionManager stompSessionManager;

    /**
     * 카프카로부터 만료된 웹소켓 메시지 수신 후
     * 해당 유저의 STOMP 세션 강제 종료
     */
    @Override
    public void disconnect(String usrSeqId) {
        boolean isThisPod = stompSessionManager.hasLocalSession(usrSeqId);


        if (!isThisPod) {
            return;
        }

        stompSessionManager.disconnectUser(usrSeqId);
    }
}
