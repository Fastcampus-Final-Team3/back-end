package com.javajober.spaceWall.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.javajober.blocks.freeBlock.dto.response.FreeBlockResponse;
import com.javajober.core.util.response.CommonResponse;
import org.springframework.stereotype.Component;

import com.javajober.blocks.freeBlock.domain.FreeBlock;
import com.javajober.blocks.freeBlock.dto.request.FreeBlockSaveRequest;
import com.javajober.blocks.freeBlock.repository.FreeBlockRepository;
import com.javajober.spaceWall.domain.BlockType;
import com.javajober.spaceWall.strategy.BlockJsonProcessor;
import com.javajober.spaceWall.strategy.BlockStrategyName;
import com.javajober.spaceWall.strategy.MoveBlockStrategy;

@Component
public class FreeBlockStrategy implements MoveBlockStrategy {
	private static final String FREE_BLOCK = BlockType.FREE_BLOCK.getEngTitle();
	private final BlockJsonProcessor blockJsonProcessor;
	private final FreeBlockRepository freeBlockRepository;

	public FreeBlockStrategy(final BlockJsonProcessor blockJsonProcessor, final FreeBlockRepository freeBlockRepository) {
		this.blockJsonProcessor = blockJsonProcessor;
		this.freeBlockRepository = freeBlockRepository;
	}

	@Override
	public void saveBlocks(final List<Object> subData, final ArrayNode blockInfoArray, final Long position) {

		subData.forEach(block -> {
			FreeBlockSaveRequest request = blockJsonProcessor.convertValue(block, FreeBlockSaveRequest.class);
			Long freeBlockId = saveFreeBlock(request);
			blockJsonProcessor.addBlockInfoToArray(blockInfoArray, freeBlockId, FREE_BLOCK, freeBlockId, "");
		});
	}

	private Long saveFreeBlock(FreeBlockSaveRequest request) {
		FreeBlock freeBlock = FreeBlockSaveRequest.toEntity(request);
		return freeBlockRepository.save(freeBlock).getId();
	}

	@Override
	public List<CommonResponse> createMoveBlockDTO(List<JsonNode> blocksWithSamePosition) {
		List<CommonResponse> subData = new ArrayList<>();
		for (JsonNode block : blocksWithSamePosition) {
			long blockId = block.path("block_id").asLong();
			FreeBlock freeBlock = freeBlockRepository.findFreeBlock(blockId);
			subData.add(FreeBlockResponse.from(freeBlock));
		}
		return subData;
	}

	@Override
	public String getStrategyName() {
		return BlockStrategyName.FreeBlockStrategy.name();
	}
}
