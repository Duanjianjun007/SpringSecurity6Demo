package com.akali.security.filter;

import com.akali.common.exception.BaseException;
import com.akali.security.model.entity.rbac.UserInfo;
import com.akali.security.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * 使用OncePerRequestFilter，来规定每个请求过来只经过这个过滤器一次
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token（规定：token是请求头中携带的）
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            // 如果请求头中不携带token，则放行，让后面的过滤器返回相应的异常信息
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String userId;
        try {
            userId = JwtUtils.parseJWT(token).getId();
        } catch (Exception e) {
            throw new BaseException("解析token异常");
        }

        // 从redis中获取用户
        UserInfo userInfo = (UserInfo)redisTemplate.opsForValue().get("login:" + userId);
        if (Objects.isNull(userInfo)) {
            throw new BaseException("用户未登录");
        }

        // 将用户信息存入SecurityContextHolder中，以便后续的过滤器从中获取用户信息
        // 转换用户并设置用户权限信息，存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 将过滤器放行
        filterChain.doFilter(request, response);
    }
}
