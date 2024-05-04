package com.akali.security.model.entity.rbac;

import lombok.Data;

@Data
public class RoleMenu {
    private Integer id;
    private Integer roleId;
    private Integer menuId;
}
