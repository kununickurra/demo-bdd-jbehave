package com.cgi.poc.jbehave.commons.context;

/**
 * Fault Object
 */
public class Fault {

    private Class clazz;
    private String message;

    public Fault(Exception e) {
        clazz = e.getClass();
        message = e.getMessage();
    }

    public Class getClazz() {
        return clazz;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Fault{" +
                "clazz=" + clazz +
                ", message='" + message + '\'' +
                '}';
    }
}
