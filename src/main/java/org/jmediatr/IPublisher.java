package org.jmediatr;

/**
 * 多播消息发布接口
 * @author rende
 */
public interface IPublisher {
    /**
     * 发布一个多播对象
     * @param notification 多播对象
     * @param <TNotification> {@link INotification>
     */
    <TNotification extends INotification> void publish(TNotification notification);
}
