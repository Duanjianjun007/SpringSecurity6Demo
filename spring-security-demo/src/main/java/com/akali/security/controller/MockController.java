package com.akali.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String helloSecurity() {
        log.info("进入hello接口...");
        return "helloSecurity";
    }
}
