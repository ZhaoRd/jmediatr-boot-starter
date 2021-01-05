package io.github.zhaord.jmediatr;

/**
 * 单播消息处理接口
 * @author rende
 * @param <TRequest> {@link IRequest}
 * @param <TResponse> 消息处理返回结果
 */
public interface IRequestHandler<TRequest extends IRequest<TResponse>, TResponse> {
    /**
     * 处理
     * @param request 单播消息
     * @return 处理结果
     */
    TResponse handle(TRequest request);
}
