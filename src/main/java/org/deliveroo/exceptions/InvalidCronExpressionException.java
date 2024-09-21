package org.deliveroo.exceptions;

public class InvalidCronExpressionException extends Exception{
    public InvalidCronExpressionException(String message){
        super(message);
    }
}
