package com.javajober.themeSetting.dto.response;

import com.javajober.themeSetting.domain.ThemeSetting;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ThemeSettingResponse {

    private Long themeSettingBlockId;
    private String theme;

    private ThemeSettingResponse(){}

    @Builder
    public ThemeSettingResponse(final Long themeSettingBlockId, final String theme) {
        this.themeSettingBlockId = themeSettingBlockId;
        this.theme = theme;
    }

    public static ThemeSettingResponse from(ThemeSetting themeSetting) {
        return ThemeSettingResponse.builder()
                .themeSettingBlockId(themeSetting.getId())
                .theme(themeSetting.getTheme())
                .build();
    }
}
