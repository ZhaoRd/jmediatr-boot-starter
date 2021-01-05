package io.github.zhaord.jmediatr;


/**
 * 没有找到单播消息处理对象的异常
 * @author rende
 */
public class NoRequestHandlerException extends MediatorException {
    private Class<?> requestClass;

    public Class<?> getRequestClass(){
        return this.requestClass;
    }

    public NoRequestHandlerException(
            Class<?> requestClass
    ) {
        this.requestClass = requestClass;
    }
}
