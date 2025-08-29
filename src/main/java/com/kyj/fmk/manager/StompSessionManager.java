package com.kyj.fmk.manager;

import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.model.ws.WsTopic;
import com.kyj.fmk.model.ws.WsRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

/**
 * 2025-08-28
 * STOMP 세션 매니저 (특정 유저 세션 강제 종료)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StompSessionManager {

    private final SimpUserRegistry userRegistry;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String,Object> redisTemplate;


    /**
     * 특정 유저 세션 강제 종료
     * @param usrSeqId
     */
    public void disconnectUser(String usrSeqId) {
        SimpUser user = userRegistry.getUser(usrSeqId);

        if (user != null) {
            user.getSessions().forEach(session -> {
                try {
                    //레디스 제거
                    redisTemplate.opsForZSet().remove(RedisKey.WS_SESSION_Z_SET_KEY,usrSeqId);

                    WsRes wsRes = new WsRes(WsTopic.TOPIC_DISCONNECT);


                    log.info("STOMP 세션 강제 종료: user={}, session={}", usrSeqId, session.getId());
                    // 특정 구독자에게 /user/queue/disconnect 메시지를 보내고 클라이언트에서 연결 종료하도록 유도
                    messagingTemplate.convertAndSendToUser(
                            usrSeqId,
                            WsTopic.TOPIC_DISCONNECT.getTopic(),
                            wsRes
                    );
                } catch (Exception e) {
                    log.error("STOMP 세션 강제 종료 실패: {}", e.getMessage(), e);
                }
            });
        }
    }

    /**
     * 특정 유저가 현재 이 파드에 세션이 있는지
     */
    public boolean hasLocalSession(String usrSeqId) {
        SimpUser user = userRegistry.getUser(usrSeqId);
        return user != null && !user.getSessions().isEmpty();
    }
}



