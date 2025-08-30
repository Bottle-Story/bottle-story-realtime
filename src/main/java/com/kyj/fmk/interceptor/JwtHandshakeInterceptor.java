package com.kyj.fmk.interceptor;


import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.model.kafka.KafkaPushLiveUserDTO;
import com.kyj.fmk.sec.jwt.JWTUtil;
import com.kyj.fmk.service.KafkaRtmPublishService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;


/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 인터셉터
 */
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String,Object> redisTemplate;
    private final KafkaRtmPublishService kafkaRtmPublishService;

    /**
     * 웹소켓 3-handshake에서 토큰을 검증 및 세션 저장
     * @param request the current request
     * @param response the current response
     * @param wsHandler the target WebSocket handler
     * @param attributes the attributes from the HTTP handshake to associate with the WebSocket
     * session; the provided attributes are copied, the original map is not used.
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            Cookie[] cookies = servletRequest.getCookies();

            String token = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("Authorization".equals(cookie.getName())) {
                        token = cookie.getValue();
                        if (!isValidToken(token)) return false;
                    }
                }
               if(token == null){
                   return false;
               }
            }else{
                return false;
            }

            //웹소켓 세션에 저장
            String usrSeqId = jwtUtil.getUsrSeqId(token);
            attributes.put("usrSeqId",usrSeqId);
            //레디스에 세션 저장

            long expireAt = System.currentTimeMillis() + RedisKey.WS_SESSION_EXPIRE_MS;

            redisTemplate.opsForZSet().add(RedisKey.WS_SESSION_Z_SET_KEY
                    ,usrSeqId,expireAt);


            //카프카 이벤트 발행
            KafkaPushLiveUserDTO rtmWsConnectKafkaDTO = new KafkaPushLiveUserDTO();
            kafkaRtmPublishService.publishPushLiveUser(rtmWsConnectKafkaDTO);
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        
    }

    /**
     * 토큰검증
     * @param token
     * @return
     */
    private boolean isValidToken(String token){
        String category = jwtUtil.getCategory(token);

        if (!category.equals("access")) {
            //액세스토큰이 아닐시
            return false;
        } else if (!jwtUtil.validate(token)) {
            //검증
            return false;

        }

        return true;
    }



}