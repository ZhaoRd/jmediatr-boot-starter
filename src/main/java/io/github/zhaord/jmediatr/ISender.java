package io.github.zhaord.jmediatr;

/**
 * 单播消息发布接口
 * @author rende
 */
public interface ISender {
    /**
     * 单播发布接口
     * @param request 单播消息
     * @param <TResponse> 返回类型
     * @return 处理结果
     */
    <TResponse> TResponse send(IRequest<TResponse> request);
}
