package com.javajober.fileBlock.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class FileBlockSaveRequests {

    List<FileBlockSaveRequest> subData;

    private FileBlockSaveRequests() {}

    public FileBlockSaveRequests(List<FileBlockSaveRequest> subData) {
        this.subData = subData;
    }
}
