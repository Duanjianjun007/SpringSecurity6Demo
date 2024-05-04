package com.akali.security.model.entity.rbac;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleInfo {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
