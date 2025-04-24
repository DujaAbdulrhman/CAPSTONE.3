package com.example.demo.API;

public class ApiException extends RuntimeException{
    public ApiException(String massage){
        super(massage);
    }
}
