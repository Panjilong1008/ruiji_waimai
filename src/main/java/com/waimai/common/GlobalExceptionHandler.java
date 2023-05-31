package com.waimai.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
* 全局异常处理
* */

//这里anntations利用注解指定处理的范围（使用RestController、controller注解的类里的所有函数都会进行异常处理）
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//使用@ResponseBody将结果封装成JSON数据返回
@ResponseBody
//日志打印
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.error(exception.getMessage());
        return R.error("失败了");
    }
}
