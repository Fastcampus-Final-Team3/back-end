package com.javajober.setting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javajober.core.message.SuccessMessage;
import com.javajober.core.util.ApiUtils;
import com.javajober.setting.dto.BackgroundSettingSaveRequest;
import com.javajober.setting.service.BackgroundSettingService;

@RequestMapping("/api/setting/background")
@RestController
public class BackgroundSettingController {

	private final BackgroundSettingService backgroundSettingService;

	public BackgroundSettingController(BackgroundSettingService backgroundSettingService) {
		this.backgroundSettingService = backgroundSettingService;
	}

	@PostMapping
	public ResponseEntity<ApiUtils.ApiResponse> save(
		@RequestPart(value = "backgroundRequest") final BackgroundSettingSaveRequest styleSetting,
		@RequestPart(value = "styleImgURL", required = false) MultipartFile styleImgURL){

		backgroundSettingService.save(styleSetting);

		return ResponseEntity.ok(ApiUtils.success(HttpStatus.CREATED, SuccessMessage.CREATE_SUCCESS, null));
	}
}
