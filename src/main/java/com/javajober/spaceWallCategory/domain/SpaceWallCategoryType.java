package com.javajober.spaceWallCategory.domain;

import java.util.Arrays;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;

import lombok.Getter;

@Getter
public enum SpaceWallCategoryType {

    CAREER("career", "취업/이직"),
    PERSONAL("personal", "개인/소개"),
    EVENT("event", "이벤트/일상"),
    ENTERPRISE("enterprise", "기업/근로양식"),
    BASIC("basic", "기본 템플릿"),
    EMPTY("empty", "없음");

    private final String engTitle;
    private final String korTitle;

    SpaceWallCategoryType(final String engTitle, final String korTitle) {
        this.engTitle = engTitle;
        this.korTitle = korTitle;
    }

    public static SpaceWallCategoryType findSpaceWallCategoryTypeByString(final String type) {
        return Arrays.stream(values())
            .filter(spaceWallCategoryType -> spaceWallCategoryType.getEngTitle().equals(type))
            .findAny()
            .orElseThrow(() -> new Exception404(ErrorMessage.INVALID_SPACE_WALL_CATEGORY_TYPE));
    }
}
