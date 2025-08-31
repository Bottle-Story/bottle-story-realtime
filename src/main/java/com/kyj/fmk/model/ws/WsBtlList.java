package com.kyj.fmk.model.ws;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 응답객체(유리병 조회 리스트)
 */
@Getter
@Setter
public class WsBtlList {
    private List<WsBtl> btlLtrNoList;
}
