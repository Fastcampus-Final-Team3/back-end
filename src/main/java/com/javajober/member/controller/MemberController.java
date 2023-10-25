package com.javajober.member.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javajober.core.util.response.ApiResponse;
import com.javajober.core.exception.ApiStatus;
import com.javajober.core.exception.ApplicationException;
import com.javajober.member.dto.MemberLoginRequest;
import com.javajober.member.dto.MemberLoginResponse;
import com.javajober.member.dto.MemberSignupRequest;
import com.javajober.member.dto.MemberSignupResponse;
import com.javajober.member.service.MemberService;
import com.javajober.core.refreshToken.service.RefreshTokenService;
import com.javajober.core.refreshToken.dto.RefreshTokenRequest;

@RestController
@Validated
@RequestMapping("/api/members")
public class MemberController {
	private final MemberService memberService;
	private final RefreshTokenService refreshTokenService;

	public MemberController(MemberService memberService,
		RefreshTokenService refreshTokenService) {
		this.memberService = memberService;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse.Response<MemberSignupResponse>> signup(@RequestBody @Valid MemberSignupRequest memberSignupRequest) {

		MemberSignupResponse data = memberService.signup(memberSignupRequest);

		return ApiResponse.response(ApiStatus.OK, "회원가입에 성공했습니다.", data);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse.Response<MemberLoginResponse>> login(@RequestBody @Valid MemberLoginRequest loginDto) {
		try {

			MemberLoginResponse data = memberService.login(loginDto);

			return ApiResponse.response(ApiStatus.OK, "로그인에 성공했습니다.", data);
		} catch (IllegalArgumentException e) {

			throw new ApplicationException(ApiStatus.NO_PERMISSION, "로그인에 실패했습니다.");
		}
	}

	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse.MessageResponse> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {

		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());

		return ApiResponse.messageResponse(ApiStatus.OK, "로그인아웃에 성공했습니다.");
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<ApiResponse.Response<MemberLoginResponse>> requestRefresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {

		MemberLoginResponse data = refreshTokenService.findRefreshToken(refreshTokenRequest);

		return ApiResponse.response(ApiStatus.OK, "토큰 재발급에 성공했습니다.", data);
	}
}