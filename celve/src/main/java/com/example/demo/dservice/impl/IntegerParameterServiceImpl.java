package com.example.demo.dservice.impl;

import com.example.demo.annotations.ParameterDispose;
import com.example.demo.dservice.IParameterDisposeService;
import com.example.demo.enums.ParameterTypes;
import com.example.demo.responses.ErrorMsssage;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
@ParameterDispose(ParameterTypes.Integer)
public class IntegerParameterServiceImpl implements IParameterDisposeService {
    @Override
    public void disposeParameter(List<String> errorMessageList, Object argValue,Integer annotationValue, StringBuilder message, Field argField ) {
        if (annotationValue.intValue() < (Integer) argValue){
            message.append(argField.getName()) .append( ": Over the maximum");
            errorMessageList.add(message.toString());
            message.delete(0, message.length());
        }
    }
}
