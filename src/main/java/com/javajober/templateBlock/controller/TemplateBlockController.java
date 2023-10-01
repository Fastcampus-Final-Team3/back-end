package com.javajober.templateBlock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javajober.core.message.SuccessMessage;
import com.javajober.core.util.ApiUtils;
import com.javajober.template.dto.request.TemplateBlockDeleteRequest;
import com.javajober.template.dto.request.TemplateBlockSaveRequest;
import com.javajober.template.dto.request.TemplateBlockSaveRequests;
import com.javajober.template.dto.response.TemplateBlockResponses;
import com.javajober.template.dto.request.TemplateBlockUpdateRequest;
import com.javajober.template.service.TemplateBlockService;

@RequestMapping("/api/wall/templateBlocks")
@RestController
public class TemplateBlockController {

	private final TemplateBlockService templateBlockService;

	public TemplateBlockController(TemplateBlockService templateBlockService) {
		this.templateBlockService = templateBlockService;
	}

	@PostMapping
	public ResponseEntity<ApiUtils.ApiResponse> createTemplateBlock(@RequestBody TemplateBlockSaveRequests<TemplateBlockSaveRequest> templateBlockSaveRequests) {

		templateBlockService.save(templateBlockSaveRequests);

		return ResponseEntity.ok(ApiUtils.success(HttpStatus.CREATED, SuccessMessage.TEMPLATE_BLOCK_SAVE_SUCCESS, null));
	}

	@GetMapping
	public ResponseEntity<ApiUtils.ApiResponse<TemplateBlockResponses>> readTemplateBlock(@RequestParam final List<Long> templateBlockIds) {

		TemplateBlockResponses response = templateBlockService.find(templateBlockIds);

		return ResponseEntity.ok(ApiUtils.success(HttpStatus.OK, SuccessMessage.TEMPLATE_BLOCK_READ_SUCCESS, response));
	}

	@PutMapping
	public ResponseEntity<ApiUtils.ApiResponse> updateTemplateBlock(@RequestBody final TemplateBlockSaveRequests<TemplateBlockUpdateRequest> templateBlockSaveRequests){

		templateBlockService.update(templateBlockSaveRequests);

		return ResponseEntity.ok(ApiUtils.success(HttpStatus.OK, SuccessMessage.TEMPLATE_BLOCK_UPDATE_SUCCESS, null));
	}

	@PutMapping("/history")
	public ResponseEntity<ApiUtils.ApiResponse> deleteTemplateBlock(@RequestBody final TemplateBlockDeleteRequest templateBlockDeleteRequest) {

		templateBlockService.delete(templateBlockDeleteRequest);

		return ResponseEntity.ok(ApiUtils.success(HttpStatus.OK, SuccessMessage.TEMPLATE_BLOCK_DELETE_SUCCESS, null));
	}
}