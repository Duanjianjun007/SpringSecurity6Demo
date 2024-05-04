package com.akali.security.security;

import com.akali.security.mapper.UserInfoMapper;
import com.akali.security.model.entity.rbac.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        UserInfo user = userInfoMapper.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }

//        // 测试时手动加的权限码
//        List<String> permissionCodes = new ArrayList<>(Arrays.asList("test", "admin"));
//        user.setPermissionCodes(permissionCodes);
        return user;
    }
}
