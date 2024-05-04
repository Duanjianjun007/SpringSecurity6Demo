package com.akali.security.model.entity.rbac;

import lombok.Data;

@Data
public class MenuInfo {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名
     */
    private String menuName;
    /**
     * 权限码
     */
    private String permissionCode;
}
