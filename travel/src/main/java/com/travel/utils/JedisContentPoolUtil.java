package com.travel.utils;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Type;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JedisContentPoolUtil {
    private static  JedisPool jp;

    private static String POOL_HOST;
    private static int POOL_PORT;
    private static int POOL_MAX_TOTAL;
    private static int POOL_MAX_IDEL;
    private static long POOL_MAX_WAIT_MILLIS;
    private static String JEDIS_AUTH;
    static {

        ResourceBundle rb = ResourceBundle.getBundle("jedisinfo");
        POOL_HOST = rb.getString("host");
        POOL_PORT = Integer.parseInt(rb.getString("port"));
        POOL_MAX_TOTAL = Integer.parseInt(rb.getString("maxTotal"));
        POOL_MAX_IDEL = Integer.parseInt(rb.getString("maxIdle"));
        POOL_MAX_WAIT_MILLIS = Long.parseLong(rb.getString("maxWaitMillis"));
        try{
            JEDIS_AUTH = rb.getString("auth");
        }catch (MissingResourceException e){
            JEDIS_AUTH = null;
        }

        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(POOL_MAX_TOTAL);
        jpc.setMaxIdle(POOL_MAX_IDEL);
        jpc.setMaxWaitMillis(POOL_MAX_WAIT_MILLIS);

        jp = new JedisPool(jpc, POOL_HOST, POOL_PORT);
    }

    public static Jedis getPoolJedisResoures(){
        Jedis resource = jp.getResource();
        if(JEDIS_AUTH != null){
            resource.auth(JEDIS_AUTH);
        }
        return resource;
    }



}
