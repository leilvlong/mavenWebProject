package com.example.demo.dservice.impl;

import com.example.demo.annotations.ParameterDispose;
import com.example.demo.dservice.IParameterDisposeService;
import com.example.demo.enums.ParameterTypes;
import com.example.demo.responses.ErrorMsssage;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
@ParameterDispose(ParameterTypes.String)
public class StringParameterServiceImpl implements IParameterDisposeService {
    @Override
    public void disposeParameter(List<String> errorMessageList, Object argValue,Integer annotationValue, StringBuilder message, Field argField) {
        String value = (String) argValue;

        if (annotationValue < value.length()){
            message.append(argField.getName()) .append( ": String exceeding the maximum length limit");
            errorMessageList.add(message.toString());
            message.delete(0, message.length());
        }
    }
}
