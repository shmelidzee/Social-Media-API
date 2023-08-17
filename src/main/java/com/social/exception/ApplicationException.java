package com.social.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Schema(name = "Application Exception", description = "Bad Request")
@Builder
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ApplicationException extends Exception {

    @Schema(description = "HTTP status code")
    @EqualsAndHashCode.Include
    private HttpStatus status;
    @Schema(description = "Error message")
    @EqualsAndHashCode.Include
    private String responseMessage;

    public ApplicationException(HttpStatus status, String responseMessage) {
        super(responseMessage);
        this.responseMessage = responseMessage;
        this.status = status;
    }
}