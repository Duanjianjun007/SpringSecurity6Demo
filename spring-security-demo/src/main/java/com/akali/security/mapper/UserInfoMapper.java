package com.akali.security.mapper;

import com.akali.security.model.entity.rbac.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo findById(Long id);
    UserInfo findByUsername(String username);
}
