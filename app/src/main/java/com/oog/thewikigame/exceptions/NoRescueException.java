package com.oog.thewikigame.exceptions;

/**
 * This exception will throw when a rescue is trying to be used when there are no more rescues left.
 */
public class NoRescueException extends RuntimeException{
    public NoRescueException(String reason){
        super(reason);
    }
}
