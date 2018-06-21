package com.lichaobao.dropbox.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * api返回类型
 */
public class MessageEntity<T> {
    int code = 200;
    String message = "success";
    T result;

    public MessageEntity() {
    }

    public MessageEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public MessageEntity(T result) {
        this.result = result;
    }

    public MessageEntity(String message) {
        this.message = message;
    }

    public MessageEntity(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
