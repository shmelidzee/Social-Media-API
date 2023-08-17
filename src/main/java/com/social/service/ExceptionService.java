package com.social.service;

import com.social.dto.UiErrorDTO;
import com.social.exception.ApplicationException;

public interface ExceptionService {

    UiErrorDTO buildUiErrorDTO(ApplicationException exception);
}