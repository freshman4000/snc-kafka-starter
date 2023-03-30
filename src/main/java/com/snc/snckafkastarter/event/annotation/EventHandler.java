package com.snc.snckafkastarter.event.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface EventHandler {

}
