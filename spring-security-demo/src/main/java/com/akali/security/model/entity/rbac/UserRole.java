package com.akali.security.model.entity.rbac;

import lombok.Data;

@Data
public class UserRole {
    private Integer id;
    private Long userId;
    private Integer roleId;
}
