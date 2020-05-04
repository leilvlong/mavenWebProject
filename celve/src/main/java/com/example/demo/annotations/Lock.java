package com.example.demo.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lock {

    String key ();

    long timeOut();
}
