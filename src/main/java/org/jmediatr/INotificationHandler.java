package org.jmediatr;

/**
 * 多播消息处理接口
 * @author rende
 */
public interface INotificationHandler<TNotification extends INotification> {

    /**
     * 处理方法
     * @param notification 多播对象
     */
    void handle(TNotification notification);
}
