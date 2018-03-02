package com.tyaathome.s1mpleweather.model.annonations.inject;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * content layout 注入注解
 * Created by tyaathome on 2017/9/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LayoutID {
    @LayoutRes
    int value();
}
