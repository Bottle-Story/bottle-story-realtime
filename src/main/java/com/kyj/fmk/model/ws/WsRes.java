package com.kyj.fmk.model.ws;

import lombok.Getter;
import lombok.Setter;
/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓세션 에서 사용되는 WS응답객체
 */
@Getter
@Setter
public class WsRes <T> {

    private WsTopic wsTopic;
    private T data;

    private WsRes(){

    }

    public WsRes(WsTopic wsTopic, T data){
        this.wsTopic = wsTopic;
        this.data = data;
    }

    public WsRes(WsTopic wsTopic){
        this.wsTopic = wsTopic;
        this.data = null;
    }
}
