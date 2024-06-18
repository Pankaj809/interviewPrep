package com.example.interviewPrep.model;

import com.example.interviewPrep.model.RequestContext;

public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    public static void setRequestContext(RequestContext context) {
        contextHolder.set(context);
    }

    public static RequestContext getRequestContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
