package com.Jai.electronic.store.ElectronicStore.exception;

public class BadAptRequest extends RuntimeException{
    public BadAptRequest(String message){
        super(message);
    }
    public BadAptRequest(){
        super("Bad Api Request");
    }

}
