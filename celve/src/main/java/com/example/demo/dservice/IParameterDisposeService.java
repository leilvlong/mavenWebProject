package com.example.demo.dservice;

import com.example.demo.responses.ErrorMsssage;

import java.lang.reflect.Field;
import java.util.List;

public interface IParameterDisposeService {
    void disposeParameter(List<String> errorMessageList, Object argValue,Integer annotationValue, StringBuilder message, Field argField );
}
