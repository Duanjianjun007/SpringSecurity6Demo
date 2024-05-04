package com.akali.security.config;

import com.akali.security.filter.JwtAuthenticationTokenFilter;
import com.akali.security.security.AccessDeniedHandlerImpl;
import com.akali.security.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// 开启方法级别的认证
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * 配置用户密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /**
     * 核心配置
     */
    @Bean
    public SecurityFilterChain csecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认登录页
//                .formLogin(AbstractHttpConfigurer::disable)
                // 前后端分离是无状态的，不需要session了，直接禁用
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 对请求进行认证
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 用户登录接口可以匿名访问
                        .requestMatchers("/user/login").anonymous()
//                        .requestMatchers("/mock/hello").permitAll()
//                        .requestMatchers("/user/getUser111").authenticated()
                        // 其余其他接口都需要认证后才能访问
                        .anyRequest().authenticated()
                )
                // 自定义Jwt请求认证过滤器，放在UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 自定义认证和授权异常响应
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }
}
