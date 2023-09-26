package com.javajober.snsBlock.dto;

import com.javajober.snsBlock.domain.SNSBlock;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SNSBlockResponse {
	private Long snsBlockId;
	private String snsUUID;
	private String snsType;
	private String snsURL;

	private SNSBlockResponse () {

	}

	@Builder
	public SNSBlockResponse(final Long snsBlockId, final String snsUUID, final String snsType, final String snsURL) {
		this.snsBlockId = snsBlockId;
		this.snsUUID = snsUUID;
		this.snsType = snsType;
		this.snsURL = snsURL;
	}

	public static SNSBlockResponse from (final SNSBlock snsBlock) {
		return SNSBlockResponse.builder()
			.snsBlockId(snsBlock.getId())
			.snsUUID(snsBlock.getSnsUUID())
			.snsType(snsBlock.getSnsURL())
			.build();
	}
}