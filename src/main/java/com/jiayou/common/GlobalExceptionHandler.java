package com.jiayou.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public R<String> exceptionHandler(SQLException e){
        log.info(e.getMessage());
        log.error("失败了");
        if(e.getMessage().contains("Duplicate entry")){
            String[] s = e.getMessage().split(" ");
            return R.error("已存在"+s[2]);
        }
        return R.error("错哪不知道");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e){
        return R.error(e.getMessage());
    }

    @ExceptionHandler(java.io.FileNotFoundException.class)
    public void tupianmeiyou(FileNotFoundException e){
        log.info(e.getMessage());
    }
}
