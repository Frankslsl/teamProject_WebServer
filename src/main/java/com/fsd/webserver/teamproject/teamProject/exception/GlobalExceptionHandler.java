package com.fsd.webserver.teamproject.teamProject.exception;

import com.fsd.webserver.teamproject.teamProject.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * exception handler
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        // Because the title property has been set unique in Mysql database, so this will handle the exception if a movie has a existing title
        log.error("cause is by : " + e.getCause());
        log.error("message : " + e.getLocalizedMessage());
        if (e.getCause().toString().contains("Duplicate entry")) {
            String s = e.getMessage().split(";")[1];
            String detail = s.split(" ")[3];
            System.out.println("detail = " + detail);
            return Result.error("Username " + detail + " is duplicated", HttpStatus.CONFLICT);
        }

        return Result.error("Error", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
