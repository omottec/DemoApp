package com.omottec.demoapp.gson;

public class Response<T> {
    public int code;
    public T data;
    public String errMsg;

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", data=" + data +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
