package io.github.zhaord.jmediatr;

/**
 * 多播消息发布接口
 * @author rende
 */
public interface IPublisher {
    /**
     * 发布一个多播对象
     * @param notification 多播对象
     */
    <TNotification extends INotification> void publish(TNotification notification);
}
