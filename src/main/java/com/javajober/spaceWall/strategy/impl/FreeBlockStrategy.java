package com.javajober.spaceWall.strategy.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.javajober.blocks.freeBlock.domain.FreeBlock;
import com.javajober.blocks.freeBlock.dto.request.FreeBlockSaveRequest;
import com.javajober.blocks.freeBlock.dto.request.FreeBlockUpdateRequest;
import com.javajober.blocks.freeBlock.dto.response.FreeBlockResponse;
import com.javajober.core.util.response.CommonResponse;
import org.springframework.stereotype.Component;

import com.javajober.blocks.freeBlock.repository.FreeBlockRepository;
import com.javajober.spaceWall.domain.BlockType;
import com.javajober.spaceWall.dto.request.BlockSaveRequest;
import com.javajober.spaceWall.strategy.BlockJsonProcessor;
import com.javajober.spaceWall.strategy.BlockStrategyName;
import com.javajober.spaceWall.strategy.MoveBlockStrategy;

@Component
public class FreeBlockStrategy implements MoveBlockStrategy {
	private final BlockJsonProcessor blockJsonProcessor;
	private final FreeBlockRepository freeBlockRepository;

	public FreeBlockStrategy(final BlockJsonProcessor blockJsonProcessor, final FreeBlockRepository freeBlockRepository) {
		this.blockJsonProcessor = blockJsonProcessor;
		this.freeBlockRepository = freeBlockRepository;
	}

	@Override
	public void saveStringBlocks(final BlockSaveRequest<?> block, final ArrayNode blockInfoArray, final Long position) {

		List<FreeBlockSaveRequest> freeBlockRequests = convertSubDataToFreeBlockSaveRequests(block.getSubData());

		List<FreeBlock> freeBlocks = convertToFreeBlocks(freeBlockRequests);

		List<FreeBlock> savedFreeBlocks = saveAllFreeBlock(freeBlocks);

		addToFreeBlockInfoArray(savedFreeBlocks, blockInfoArray, position, block.getBlockUUID());
	}

	@Override
	public void saveBlocks(BlockSaveRequest<?> block, ArrayNode blockInfoArray, Long position) {
		List<FreeBlockSaveRequest> freeBlockRequests = convertSubDataToFreeBlockSaveRequests(block.getSubData());

		List<FreeBlock> freeBlocks = convertToFreeBlocks(freeBlockRequests);

		List<FreeBlock> savedFreeBlocks = saveAllFreeBlock(freeBlocks);

		addToFreeBlockInfoArray(savedFreeBlocks, blockInfoArray, position, block.getBlockUUID());
	}

	private List<FreeBlockSaveRequest> convertSubDataToFreeBlockSaveRequests(final List<?> subData) {
		List<FreeBlockSaveRequest> freeBlockRequests = new ArrayList<>();

		subData.forEach(block -> {
			FreeBlockSaveRequest request = blockJsonProcessor.convertValue(block, FreeBlockSaveRequest.class);
			freeBlockRequests.add(request);
		});
		return freeBlockRequests;
	}

	private List<FreeBlock> convertToFreeBlocks(final List<FreeBlockSaveRequest> freeBlockRequests) {
		return freeBlockRequests.stream()
			.map(FreeBlockSaveRequest::toEntity)
			.collect(Collectors.toList());
	}

	private List<FreeBlock> saveAllFreeBlock(final List<FreeBlock> freeBlocks) {
		return freeBlockRepository.saveAll(freeBlocks);
	}

	private void addToFreeBlockInfoArray (final List<FreeBlock> savedFreeBlocks, final ArrayNode blockInfoArray, final Long position, final String freeBlockUUID) {
		savedFreeBlocks.forEach(savedFreeBlock ->
			blockJsonProcessor.addBlockInfoToArray(blockInfoArray, position, BlockType.FREE_BLOCK, savedFreeBlock.getId(), freeBlockUUID)
		);
	}

	@Override
	public List<CommonResponse> createMoveBlockDTO(final List<JsonNode> blocksWithSamePosition) {
		List<CommonResponse> subData = new ArrayList<>();
		for (JsonNode block : blocksWithSamePosition) {
			long blockId = block.path("block_id").asLong();
			com.javajober.blocks.freeBlock.domain.FreeBlock freeBlock = freeBlockRepository.findFreeBlock(blockId);
			subData.add(FreeBlockResponse.from(freeBlock));
		}
		return subData;
	}

	@Override
	public Set<Long> updateBlocks(final BlockSaveRequest<?> blocks, final ArrayNode blockInfoArray, final Long position) {

		List<FreeBlock> freeBlocks = new ArrayList<>();

		blocks.getSubData().forEach(block -> {
			FreeBlockUpdateRequest request = blockJsonProcessor.convertValue(block, FreeBlockUpdateRequest.class);
			FreeBlock freeBlock = saveOrUpdateFreeBlock(request);
			freeBlocks.add(freeBlock);
		});
		List<FreeBlock> updatedFreeBlocks = freeBlockRepository.saveAll(freeBlocks);
		Set<Long> updateFreeBlockIds = updatedFreeBlocks.stream().map(FreeBlock::getId).collect(Collectors.toCollection(LinkedHashSet::new));
		updateFreeBlockIds.forEach(blockId ->
			blockJsonProcessor.addBlockInfoToArray(blockInfoArray, position, BlockType.FREE_BLOCK, blockId, blocks.getBlockUUID()));
		return updateFreeBlockIds;
	}

	private FreeBlock saveOrUpdateFreeBlock(FreeBlockUpdateRequest request) {

		if(request.getFreeBlockId() == null) {
			return FreeBlockUpdateRequest.toEntity(request);
		}

		FreeBlock freeblock = freeBlockRepository.findFreeBlock(request.getFreeBlockId());
		freeblock.update(request);

		return freeblock;
	}

	@Override
	public String getStrategyName() {
		return BlockStrategyName.FreeBlockStrategy.name();
	}
}
