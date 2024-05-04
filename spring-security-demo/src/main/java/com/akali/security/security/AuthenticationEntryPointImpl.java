package com.akali.security.security;

import com.akali.common.model.RestResponseObject;
import com.akali.common.utils.JSONUtils;
import com.akali.security.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义用户认证异常响应
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RestResponseObject restResponseObject = new RestResponseObject(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "用户名或密码错误，请检查后重新登录", null);
        String result = JSONUtils.toJson(restResponseObject);
        WebUtils.renderString(response, result);
    }
}
