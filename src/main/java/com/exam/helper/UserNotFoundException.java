package com.exam.helper;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("User with this username is already there in Database !! Try with another Username");
    }
       public UserNotFoundException(String msg){  super(msg);  }

}
