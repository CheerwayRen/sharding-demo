package com.scpg.sharding.demo.domain;


import java.io.Serializable;

/**
 * @program: study
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 10:56
 */

public class ApiRespJsonObj<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }
    private ApiRespJsonObj(T data) {
        this.code = "200";
        this.msg = "success";
        this.data = data;
    }


    public static <T> ApiRespJsonObj success(T data) {
        return new ApiRespJsonObj(data);
    }

    public static ApiRespJsonObj success() {
        return new ApiRespJsonObj(null);
    }

    public static ApiRespJsonObj fail(String code, String msg) {
        return new ApiRespJsonObj(code, msg, null);
    }

    public ApiRespJsonObj(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}