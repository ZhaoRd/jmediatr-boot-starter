package org.jmediatr;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 中介者实现类
 * <p>
 * 依赖 {@link ApplicationContext}
 *
 * 在实现{@link IPublisher} 和 {@link IRequest}的方法中，根据消息类型和和返回结果类型，
 * 从Spring上下文中获取对应的处理对象,在执行对象方法。
 * 多播会循环调用每个对象。
 * 单播只调用第一个,如果有多个单播消息处理，需要处理对象注入的优先级，如果没有单播对象实现，抛出{@link NoRequestHandlerException}
 * @author rende
 */
@Component
public class Mediator implements IMediator, ApplicationContextAware {

    private ApplicationContext context;

    /**
     * 发布同步
     * <p>
     * 根据通知类型和INotificationHandler,从ApplicationContext获取Handler的BeanNames,
     * 将 BeanNames 转化为 INotificationHandler 的实例，每个实例调用一次handler
     *
     * @param notification    通知内容
     * @param <TNotification> 通知类型
     */
    @Override
    public <TNotification extends INotification> void publish(TNotification notification) {

        ResolvableType handlerType = ResolvableType.forClassWithGenerics(
                INotificationHandler.class, notification.getClass());

        String[] beanNamesForType = this.context.getBeanNamesForType(handlerType);
        List<INotificationHandler<TNotification>> list = new ArrayList<>();
        for (String beanBane :
                beanNamesForType) {
            list.add((INotificationHandler<TNotification>) this.context.getBean(beanBane));
        }
        list.forEach(h -> h.handle(notification));
    }

    /**
     * 发送求取
     * <p>
     * 根据request类型，获取到response类型，
     * 根据IRequestHandler、request类型、response类型从ApplicationContext获取
     * IRequestHandler实例列表，取第一个实例执行handler方法。
     * <p>
     * <p>
     * 如果为找到handler实例，抛出NoRequestHandlerException异常
     *
     * @param request     请求
     * @param <TResponse> 响应类型
     * @return 响应结果
     */
    @Override
    public <TResponse> TResponse send(IRequest<TResponse> request) {
        Type[] genericInterfaces = request.getClass().getGenericInterfaces();

        Type responseType = null;

        for (Type type : genericInterfaces) {
            if ((type instanceof ParameterizedType)) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (!parameterizedType.getRawType().equals(IRequest.class)) {
                    continue;
                }
                responseType = parameterizedType.getActualTypeArguments()[0];
                break;
            }
        }

        if (responseType == null) {
            // 抛出异常
            throw new NoRequestHandlerException(request.getClass());
        }


        Class<?> requestClass = request.getClass();
        Class<?> responseClass = (Class<?>) responseType;

        ResolvableType handlerType = ResolvableType.forClassWithGenerics(
                IRequestHandler.class,
                requestClass,
                responseClass);

        String[] beanNamesForType = this.context.getBeanNamesForType(handlerType);
        List<IRequestHandler<IRequest<TResponse>, TResponse>> list = new ArrayList<>();
        for (String beanBane :
                beanNamesForType) {
            list.add((IRequestHandler<IRequest<TResponse>, TResponse>) this.context.getBean(beanBane));
        }

        if (list.isEmpty()) {
            throw new NoRequestHandlerException(request.getClass());
        }

        return list.stream()
                .findFirst()
                .map(h -> h.handle(request))
                .orElseThrow(() -> new NoRequestHandlerException(request.getClass()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
