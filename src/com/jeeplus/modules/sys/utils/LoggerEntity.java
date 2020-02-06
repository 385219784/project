package com.jeeplus.modules.sys.utils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by frinder on 2017/5/24.
 */
public class LoggerEntity {
 
    private String method;
    private String uri;
    private Object[] args;
    private Object result;
    private String operator;
    private String appName;
 
    /**
     * 获取当前对象
     *
     * @param method
     * @param uri
     * @param args
     * @param result
     * @return
     */
    public LoggerEntity get(String method, String uri, Object[] args, Object result, String operator, String appName) {
        setMethod(method);
        setUri(uri);
        setArgs(args);
        setResult(result);
        setOperator(operator);
        setAppName(appName);
        return this;
    }
 
    public String getMethod() {
        return method;
    }
 
    public void setMethod(String method) {
        this.method = method;
    }
 
    public String getUri() {
        return uri;
    }
 
    public void setUri(String uri) {
        this.uri = uri;
    }
 
    public Object[] getArgs() {
        return args;
    }
 
    public void setArgs(Object[] args) {
        this.args = args;
    }
 
    public Object getResult() {
        return result;
    }
 
    public void setResult(Object result) {
        this.result = result;
    }
 
    public String getOperator() {
        return operator;
    }
 
    public void setOperator(String operator) {
        this.operator = operator;
    }
 
    public String getAppName() {
        return appName;
    }
 
    public void setAppName(String appName) {
        this.appName = appName;
    }
 
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
