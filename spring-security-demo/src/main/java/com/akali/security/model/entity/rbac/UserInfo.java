package com.akali.security.model.entity.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserInfo implements UserDetails {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别：0-女 1-男
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 创建时间
     */
    @JsonIgnore
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 用户的权限码集合
     */
    private List<String> permissionCodes;

    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    @JsonIgnore
    private boolean accountNonExpired = true;

    @JsonIgnore
    private boolean accountNonLocked  = true;

    @JsonIgnore
    private boolean credentialsNonExpired  = true;

    @JsonIgnore
    private boolean enabled  = true;

    /**
     * 封装用户权限
     * @return 用户权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!CollectionUtils.isEmpty(authorities)) {
            return authorities;
        }


        authorities = new ArrayList<>();
        if (CollectionUtils.isEmpty(permissionCodes)) {
            return authorities;
        }

        for (String permissionCode : permissionCodes) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
            authorities.add(simpleGrantedAuthority);
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
