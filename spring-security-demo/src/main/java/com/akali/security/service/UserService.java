package com.akali.security.service;

import com.akali.common.model.RestResponseObject;
import com.akali.security.model.vo.UserLoginReq;

public interface UserService {

    RestResponseObject login(UserLoginReq userLoginReq);

    RestResponseObject logout();
}
