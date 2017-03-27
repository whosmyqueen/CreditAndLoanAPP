package com.zhph.creditandloanappy.injector;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by 郑志辉 on 16/9/5.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
