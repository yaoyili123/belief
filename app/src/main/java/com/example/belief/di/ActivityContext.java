package com.example.belief.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

//将该注解定义为qualifier annotations（JAVAEE6特性:CDI），用于提供同一类型的不同实现
@Qualifier
//处理注解的时期
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {

}
