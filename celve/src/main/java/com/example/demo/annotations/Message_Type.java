package com.example.demo.annotations;

import com.example.demo.enums.MessageType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Message_Type {

    MessageType value();
}
