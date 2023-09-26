package com.javajober.template.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javajober.core.message.SuccessMessage;
import com.javajober.core.util.ApiUtils;
import com.javajober.template.dto.TemplateBlockRequest;
import com.javajober.template.dto.TemplateBlockRequests;
import com.javajober.template.dto.TemplateResponse;
import com.javajober.template.service.TemplateBlockService;

@RestController
public class TemplateBlockController {
	private final TemplateBlockService templateBlockService;

	public TemplateBlockController(TemplateBlockService templateBlockService) {
		this.templateBlockService = templateBlockService;
	}

	@PostMapping("/templateBlock")
	public ResponseEntity<?> createTemplateBlock(@RequestBody TemplateBlockRequests<TemplateBlockRequest> templateBlockRequests){

		templateBlockService.save(templateBlockRequests);

		return ResponseEntity.ok(ApiUtils.success(SuccessMessage.TEMPLATE_BLOCK_SAVE_SUCCESS));
	}
}