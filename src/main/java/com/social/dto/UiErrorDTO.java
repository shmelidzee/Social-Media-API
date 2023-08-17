package com.social.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "UI Error", description = "Error message")
@Getter
@Setter
@Builder
public class UiErrorDTO {

    @Schema(description = "Error code")
    private String code;
    @Schema(description = "Error message")
    private String message;

    @Override
    public String toString() {
        return "UiErrorDTO{" +
                "code='" + code + "\n" +
                "message='" + message +
                '}';
    }
}