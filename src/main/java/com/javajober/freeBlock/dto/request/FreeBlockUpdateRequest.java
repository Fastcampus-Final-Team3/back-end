package com.javajober.freeBlock.dto.request;

import com.javajober.freeBlock.domain.FreeBlock;
import lombok.Getter;


@Getter
public class FreeBlockUpdateRequest {

    private Long freeBlockId;
    private String freeTitle;
    private String freeContent;

    public FreeBlockUpdateRequest() {
    }

    public static FreeBlock toEntity(FreeBlockUpdateRequest freeBlockUpdateRequest) {
        return FreeBlock.builder()
                .freeTitle(freeBlockUpdateRequest.getFreeTitle())
                .freeContent(freeBlockUpdateRequest.getFreeContent())
                .build();
    }
}
