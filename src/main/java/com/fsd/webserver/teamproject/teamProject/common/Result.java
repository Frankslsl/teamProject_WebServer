package com.fsd.webserver.teamproject.teamProject.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <T> The Data need to return to frontend
 */
@Data
public class Result<T> {

    private HttpStatus code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> Result<T> success(T object, HttpStatus code) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = code;
        return result;
    }

    public static <T> Result<T> error(String msg, HttpStatus code) {
        Result result = new Result();
        result.msg = msg;
        result.code = code;
        return result;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
