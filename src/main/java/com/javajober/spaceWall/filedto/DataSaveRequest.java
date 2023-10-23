package com.javajober.spaceWall.filedto;

import java.util.List;

import com.javajober.core.util.response.CommonResponse;
import com.javajober.spaceWall.dto.request.BlockSaveRequest;
import com.javajober.blocks.styleSetting.filedto.StyleSettingSaveRequest;
import com.javajober.blocks.wallInfoBlock.filedto.WallInfoBlockSaveRequest;
import lombok.Getter;

@Getter
public class DataSaveRequest {

	private String category;
	private Long memberId;
	private Long spaceId;
	private String shareURL;
	private WallInfoBlockSaveRequest wallInfoBlock;
	private List<BlockSaveRequest<CommonResponse>> blocks;
	private StyleSettingSaveRequest styleSetting;
  
	private DataSaveRequest() {

	}

	public DataSaveRequest(final String category, final Long memberId, Long spaceId, final String shareURL, final WallInfoBlockSaveRequest wallInfoBlock, final List<BlockSaveRequest<CommonResponse>> blocks, final StyleSettingSaveRequest styleSetting) {
		this.category = category;
		this.memberId = memberId;
		this.spaceId = spaceId;
		this.shareURL = shareURL;
		this.wallInfoBlock = wallInfoBlock;
		this.blocks = blocks;
		this.styleSetting = styleSetting;
	}
}