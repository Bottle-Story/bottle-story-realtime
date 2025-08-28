package com.kyj.fmk.interceptor;

import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.model.StompPrincipal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
/**
 * 2025-08-28
 * @author 김용준
 * STOMP 인터셉터
 */
public class StompInterceptor implements ChannelInterceptor {


    /**
     * STOMP 프로토콜 내에서 인터셉터를 수행
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Handshake에서 넣은 userId 가져오기
            String usrSeqId = (String) accessor.getSessionAttributes().get("usrSeqId");
            System.out.println("usrSeqId = " + usrSeqId);
            if (usrSeqId == null) {
                throw new KyjSysException(CmErrCode.SEC010);
            }

            // Principal 설정
            accessor.setUser(new StompPrincipal(usrSeqId));
        }

        return message;
    }
}
