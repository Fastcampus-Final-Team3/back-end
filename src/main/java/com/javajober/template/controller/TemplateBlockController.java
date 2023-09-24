package com.javajober.template.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javajober.core.util.ApiUtils;
import com.javajober.entity.SpaceWallCategoryType;
import com.javajober.template.dto.MemberAuthResponse;
import com.javajober.entity.SpaceType;
import com.javajober.template.dto.TemplateResponse;
import com.javajober.template.service.TemplateBlockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wall/templates")
public class TemplateBlockController {
	private final TemplateBlockService templateBlockService;

	@GetMapping("/auth")
	public ResponseEntity<?> getTemplateAuthList(@RequestParam SpaceType spaceType, @RequestParam Long memberId) {
		MemberAuthResponse memberAuthResponse = templateBlockService.getTemplateAuthList(spaceType, memberId);
		return ResponseEntity.ok(ApiUtils.success(memberAuthResponse));
	}

	@GetMapping
	public ResponseEntity<?> getTemplateRecommend(@RequestParam SpaceWallCategoryType category){
		TemplateResponse templateResponse = templateBlockService.getTemplateRecommend(category);
		return ResponseEntity.ok(ApiUtils.success(templateResponse));
	}

	@GetMapping("/lists")
	public ResponseEntity<?> getTemplateCategoryList(@RequestParam SpaceWallCategoryType category){
		TemplateResponse templateResponse = templateBlockService.getTemplateRecommend(category);
		return ResponseEntity.ok(ApiUtils.success(templateResponse));
	}

	@GetMapping(params = "search")
	public ResponseEntity<?> getSearchTemplatesByTitle(@RequestParam String search){
		TemplateResponse templateResponse = templateBlockService.getSearchTemplatesByTitle(search);
		return ResponseEntity.ok(ApiUtils.success(templateResponse));
	}
}
