package com.snc.snckafkastarter.event.handler;

import com.snc.snckafkastarter.event.annotation.Event;
import com.snc.snckafkastarter.event.annotation.EventHandler;
import com.snc.snckafkastarter.event.exception.WrongEventMethodException;
import com.snc.snckafkastarter.models.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.CGLIB_CLASS_SEPARATOR;

@Configuration
@Slf4j
public class EventHandlerManagerBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        EventHandler eventHandler = bean.getClass().getAnnotation(EventHandler.class);
        if (eventHandler != null) {
            EventHandlerManager eventHandlerManager = applicationContext.getBean(EventHandlerManager.class);
            List<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Event.class))
                    .collect(Collectors.toList());
            if (methods.isEmpty()) {
                if (isProxy(bean.getClass())) {
                    Class<?> superClass = bean.getClass().getSuperclass();
                    Method[] superclassMethods = superClass.getMethods();
                    for (Method superClassMethod : superclassMethods) {
                        Event eventAnnotation = superClassMethod.getAnnotation(Event.class);
                        if (eventAnnotation != null) {
                            try {
                                Method proxyMethod =
                                        bean.getClass().getMethod(superClassMethod.getName(), KafkaMessage.class);
                                addEventHandler(bean, eventHandlerManager, eventAnnotation, proxyMethod);
                            } catch (NoSuchMethodException exception) {
                                log.error("Error processing annotation @" + EventHandler.class.getName());
                                throw new WrongEventMethodException(
                                        superClass.getName(),
                                        superClassMethod.getName(),
                                        KafkaMessage.class.getName(),
                                        exception);
                            }
                        }
                    }
                }
            } else {
                for (Method method : methods) {
                    Event eventAnnotation = method.getAnnotation(Event.class);
                    addEventHandler(bean, eventHandlerManager, eventAnnotation, method);
                }
            }
        }
        return bean;
    }

    private void addEventHandler(Object bean,
                                 EventHandlerManager eventHandlerManager,
                                 Event eventAnnotation,
                                 Method method) {
        log.info("Found handler for event {} : {}.{}",
                eventAnnotation.value(),
                bean.getClass().getName(),
                method.getName());
        eventHandlerManager.addHandler(eventAnnotation.value(), event -> {
            try {
                method.invoke(bean, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean isProxy(Class<?> clazz) {
        return Proxy.isProxyClass(clazz)
                || clazz.getName().contains(CGLIB_CLASS_SEPARATOR);
    }
}
