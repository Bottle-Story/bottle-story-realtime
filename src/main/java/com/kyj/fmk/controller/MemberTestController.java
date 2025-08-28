package com.kyj.fmk.controller;

import com.kyj.fmk.core.file.FileService;

import com.kyj.fmk.sec.annotation.PublicEndpoint;
import com.kyj.fmk.service.WebSocketSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realtime")
public class MemberTestController {

    private final SimpMessagingTemplate messagingTemplate;

    private final RedisTemplate<String,Object> redisTemplate;
    private final  FileService fileService;
    private final WebSocketSessionService webSocketSessionService;
    @PublicEndpoint
    @GetMapping("/test")
    public String test(){
//        messagingTemplate.convertAndSendToUser(
//                "0",                        // Principal.getName() 값
//                "/topic",        // 클라이언트 구독 경로
//                "hi222"
//        );
//        Map map = new HashMap();
//        map.put("gggg","Asdasd");
//        messagingTemplate.convertAndSend("/topic/test",map);
        webSocketSessionService.disconnect("0");
        return "realtime";
    }
}
