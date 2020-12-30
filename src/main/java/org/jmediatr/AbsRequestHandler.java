package org.jmediatr;

/**
 * 单播处理基类
 * @author rende
 */
public abstract class AbsRequestHandler<TRequest extends IRequest<TResponse>, TResponse>
        implements IRequestHandler<TRequest, TResponse> {

    /**
     * 处理函数
     * @param request 类型消息
     * @return 处理返利结果
     */
    @Override
    public abstract TResponse handle(TRequest request);
}
