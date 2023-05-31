package com.waimai.controller;

import com.waimai.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("RegisterEmail")
    public String getCheckCode(@RequestParam String memberEmail){
        System.out.println("邮箱为"+memberEmail);
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(memberEmail, "注册验证码", message);
        }catch (Exception e){
            return "500";
        }
        return checkCode;
    }

}
