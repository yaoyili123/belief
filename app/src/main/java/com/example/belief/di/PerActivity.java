package com.example.belief.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

//定义scope注解
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
