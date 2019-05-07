package com.example.belief.data.network.model;

public class ResponseWrapper<T> {

    private int status;

    private String message;

    private T data;

    public ResponseWrapper(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){
        return status == 200;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
