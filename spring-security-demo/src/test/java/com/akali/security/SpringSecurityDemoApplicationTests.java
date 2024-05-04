package com.akali.security;

import com.akali.security.mapper.UserInfoMapper;
import com.akali.security.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void contextLoads() {
        System.out.println("项目创建成功。。");
    }

    @Test
    void testJwtUtils() throws Exception{
//        System.out.println(JwtUtils.createJWT("1213", null, null));
        String st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjEzIiwiaXNzIjoieXUiLCJpYXQiOjE3MTQ2MzMzNDMsImV4cCI6MTcxNDYzNjk0M30.wDz_Wj0VxwVmyePL5FNlYxZu9yR_uBPiHb5MrndowVA";
        Claims claims = JwtUtils.parseJWT(st);
        String id = claims.getId();
        System.out.println(id);
//        String testStr = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoieXUiLCJpYXQiOjE3MTQ2MzI2ODIsImV4cCI6MTcxNDYzNjI4Mn0.2e2fNcVaoGr6_XfS3ZIpHeXTCZWWRJQhriHrEg_WHHk";
//        System.out.println(JwtUtils.parseJWT(testStr));
    }

    @Test
    void testUserInfoMapper() {
        System.out.println(userInfoMapper.findById(1L));
        System.out.println(userInfoMapper.findByUsername("胡八一"));
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        String encode1 = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        System.out.println(encode1);
    }

    @Test
    void getAuthenticationConfiguration() throws Exception {
    }
}
