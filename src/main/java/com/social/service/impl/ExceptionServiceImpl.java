package com.social.service.impl;

import com.social.dto.UiErrorDTO;
import com.social.exception.ApplicationException;
import com.social.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExceptionServiceImpl implements ExceptionService {

    private static final String UNKNOWN_ERROR_ON_SERVER = "UNKNOWN ERROR ON SERVER";

    /**
     * Create error for user
     * If exception is null code message will be UNKNOWN
     *
     * @param exception - data exception
     */
    @Override
    public UiErrorDTO buildUiErrorDTO(ApplicationException exception) {
        if (isNull(exception)) {
            return buildError("500", UNKNOWN_ERROR_ON_SERVER);
        }
        return buildError(String.valueOf(exception.getStatus().value()), exception.getResponseMessage());
    }

    private UiErrorDTO buildError(String code, String message) {
        return UiErrorDTO.builder()
                .code(code)
                .message(message)
                .build();
    }
}