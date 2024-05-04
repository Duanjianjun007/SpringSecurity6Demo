package com.akali.security.model.vo;

import lombok.Data;

@Data
public class UserLoginReq {
    private String username;
    private String password;
}
