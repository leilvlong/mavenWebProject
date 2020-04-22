package com.example.demo.annotations;

import com.example.demo.enums.ParameterTypes;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ParameterDispose {
    ParameterTypes value();
}
