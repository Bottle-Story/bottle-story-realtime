package com.kyj.fmk.model;

import lombok.Getter;

/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓세션 에서 사용되는 WS응답 객체의 타입 이넘
 */
@Getter
public enum WsTopic {
    TOPIC_DISCONNECT("TOPIC_DISCONNECT","/topic/disconnect"),
    TOPIC_WTHR_BGM("TOPIC_WTHR_BGM","/topic/wthrBgm"),
    TOPIC_BTL_LIST("TOPIC_BTL_LIST","/topic/btlList"),
    TOPIC_BTL_REPLY("TOPIC_BTL_REPLY","/topic/btlReply"),
    TOPIC_LIVE_USER("TOPIC_LIVE_USER","/topic/liveUser");

    private String type;
    private String topic;

     WsTopic(String type, String topic){
        this.type=type;
        this.topic=topic;
    }
}
