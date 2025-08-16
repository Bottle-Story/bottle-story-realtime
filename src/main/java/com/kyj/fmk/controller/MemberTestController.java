package com.kyj.fmk.controller;

import com.kyj.fmk.core.file.FileService;

import com.kyj.fmk.sec.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realtime")
public class MemberTestController {


    private final RedisTemplate<String,Object> redisTemplate;
    private final  FileService fileService;

    @PublicEndpoint
    @GetMapping("/test")
    public String test(){
        redisTemplate.opsForValue().set("dev","1234");
        return "realtime";
    }
}
