package xyz.txcplus.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这是一个自定义的注解(Annotation)类 在定义注解(Annotation)类时使用了另一个注解类Retention
 * 在注解类上使用另一个注解类，那么被使用的注解类就称为元注解
 * @author: TXC
 * @date: 2020-07-03 16:32
 */

@Retention(RetentionPolicy.RUNTIME)// 此注解决定了此次定义的注解类MyAnnotation的生命周期
@Target({ElementType.METHOD,ElementType.TYPE}) // Target注解决定MyAnnotation注解可以加在哪些成分上，如加在类身上，或者属性身上，或者方法身上等成分
public @interface MyAnnotation {
    String value() default "";
}
