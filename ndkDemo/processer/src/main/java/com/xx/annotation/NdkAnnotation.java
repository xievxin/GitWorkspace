package com.xx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xievxin on 2017/8/31.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface NdkAnnotation {
}
