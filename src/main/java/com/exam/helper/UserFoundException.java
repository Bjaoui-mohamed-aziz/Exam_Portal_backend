package com.exam.helper;

public class UserFoundException extends Exception{
    public UserFoundException(){
        super("User with this username not Found");
    }
    public UserFoundException(String msg){  super(msg);  }
}
