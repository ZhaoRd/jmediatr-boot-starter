package org.jmediatr;

/**
 * 多播处理基类
 * @author rende
 */
public abstract class AbsNotificationHandler<TNotification extends INotification>
        implements INotificationHandler<TNotification> {

    /**
     * 处理函数
     * @param notification 通知和类型
     */
    @Override
    public abstract void handle(TNotification notification);
}
