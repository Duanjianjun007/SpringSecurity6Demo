package com.akali.security.security;

import com.akali.common.model.RestResponseObject;
import com.akali.common.utils.JSONUtils;
import com.akali.security.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义授权异常响应
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RestResponseObject restResponseObject = new RestResponseObject(String.valueOf(HttpStatus.FORBIDDEN.value()), "您的权限不足", null);
        String result = JSONUtils.toJson(restResponseObject);
        WebUtils.renderString(response, result);
    }
}
