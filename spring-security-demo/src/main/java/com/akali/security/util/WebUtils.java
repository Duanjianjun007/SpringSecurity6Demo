package com.akali.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j
public class WebUtils {
    private WebUtils() {}

    public static void renderString(HttpServletResponse response, String result) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            log.error("转换response异常，errorMsg：{}", e.getMessage(), e);
        }
    }
}
