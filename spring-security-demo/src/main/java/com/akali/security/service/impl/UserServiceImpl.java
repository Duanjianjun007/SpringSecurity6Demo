package com.akali.security.service.impl;

import com.akali.common.model.RestResponseObject;
import com.akali.security.model.entity.rbac.UserInfo;
import com.akali.security.model.vo.UserLoginReq;
import com.akali.security.service.UserService;
import com.akali.security.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public RestResponseObject login(UserLoginReq userLoginReq) {
        // 调用AuthenticationManager接口中的authenticate方法进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginReq.getUsername(), userLoginReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证失败，给出提示
        if (Objects.isNull(authentication)) {
            return RestResponseObject.error("登录失败");
        }

        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        String userId = userInfo.getId().toString();

        // 认证成功，生成jwt jwt在RestResponseObject中返回
        String jwt = JwtUtils.createJWT(userId, null, null);
        // 将完整的用户信息存入redis userId作为key 用户对象作为value
        UserInfo redisUser = new UserInfo();
        redisUser.setId(userInfo.getId());
        redisUser.setUsername(userInfo.getUsername());
        redisUser.setPassword(userInfo.getPassword());
        redisUser.setPermissionCodes(userInfo.getPermissionCodes());
        redisUser.setPhoneNumber(userInfo.getPhoneNumber());
        redisTemplate.opsForValue().set("login:" + userId, redisUser);
        return RestResponseObject.ok("登录成功", jwt);
    }

    @Override
    public RestResponseObject logout() {
        // 获取SecurityContextHolder中的登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        // 将redis中的用户信息删除
        Long userId = userInfo.getId();
        redisTemplate.delete("login:" + userId);
        return RestResponseObject.ok("注销成功");
    }
}
