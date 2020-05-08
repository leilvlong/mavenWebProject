package com.springcloud.demo.zuulcomponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloud.demo.pojo.Response;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Component
public class ZuulFilterImpl extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String  token = (String) request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            Response response = new Response("zuul not token", 405);

            ObjectMapper objectMapper = new ObjectMapper();
            String value = null;
            try {
                value = objectMapper.writeValueAsString(response);
                HttpServletResponse servletResponse = currentContext.getResponse();
                OutputStream writer = servletResponse.getOutputStream();
                writer.write(value.getBytes());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("__________________zuul 起作用了__________________");
        return null;
    }
}
