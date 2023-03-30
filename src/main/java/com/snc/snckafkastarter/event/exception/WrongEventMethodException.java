package com.snc.snckafkastarter.event.exception;

public class WrongEventMethodException extends RuntimeException {
    private static final String MESSAGE =
            "Method %s.%s is wrong.\n" +
                    "The method annotated annotation @Event must be public and take an object of type %s as an argument";

    public WrongEventMethodException(String className,
                                     String methodName,
                                     String argumentTypeName,
                                     Exception cause) {
        super(String.format(MESSAGE, className, methodName, argumentTypeName), cause);

    }
}
