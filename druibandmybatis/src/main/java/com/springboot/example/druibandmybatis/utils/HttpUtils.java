package com.springboot.example.druibandmybatis.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @功能说明：Http 相关操作<BR>
 * @创建人员：Chase Wang<BR>
 * @变更记录：<BR> 1、2018年9月18日 Chase Wang 新建类
 */
public class HttpUtils {
    private final static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest req = null;
        try {
            req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("HttpUtils  getHttpServletRequest Error:" + e.getMessage());
        }
        return req;
    }


    /**
     * @创建日期: 2018/11/15
     * @功能说明: 获取session
     * @参数说明:
     * @返回说明:
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
            log.error("HttpUtils  getSession Error:" + e.getMessage());
        }
        return session;
    }






}
