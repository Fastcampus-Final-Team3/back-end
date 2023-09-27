package com.javajober.snsBlock.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SNSBlockDeleteRequest {
    List<Long> snsBlockIds;

    private SNSBlockDeleteRequest() {

    }
}
