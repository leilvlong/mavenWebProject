package com.springboot.example.druibandmybatis.i18n;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*// MyMessageSourceService是我自己的接口 你也可以不需要。使用@Compnent("messageSource")注解就行
@Service("messageSource")
public class MyMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    ResourceLoader resourceLoader;

    // 这个是用来缓存数据库中获取到的配置的 数据库配置更改的时候可以调用reload方法重新加载
    // 当然 实际使用者也可以不使用这种缓存的方式
    private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);



    private final Logger logger = LoggerFactory.getLogger(MyMessageSource.class);

    *//**
     * 初始化
     *//*
    @PostConstruct
    public void init() {
        this.reload();
    }

    *//**
     * 重新将数据库中的国际化配置加载
     *//*
    public void reload() {
        LOCAL_CACHE.clear();
        LOCAL_CACHE.putAll(loadAllMessageResourcesFromDB());
    }

    *//**
     * 从数据库中获取所有国际化配置 这边可以根据自己数据库表结构进行相应的业务实现
     * 对应的语言能够取出来对应的值就行了 无需一定要按照这个方法来
     *//*
    public Map<String, Map<String, String>> loadAllMessageResourcesFromDB() {
        List<SysI18nBO> list = sysI18nService.findList(new SysI18nAO());
        if (CollectionUtils.isNotEmpty(list)) {
            final Map<String, String> zhCnMessageResources = new HashMap<>(list.size());
            final Map<String, String> enUsMessageResources = new HashMap<>(list.size());
            final Map<String, String> idIdMessageResources = new HashMap<>(list.size());
            for (SysI18nBO bo : list) {
                String name = bo.getModel() + "." + bo.getName();
                String zhText = bo.getZhCn();
                String enText = bo.getEnUs();
                String idText = bo.getInId();
                zhCnMessageResources.put(name, zhText);
                enUsMessageResources.put(name, enText);
                idIdMessageResources.put(name, idText);
            }
            LOCAL_CACHE.put("zh", zhCnMessageResources);
            LOCAL_CACHE.put("en", enUsMessageResources);
            LOCAL_CACHE.put("in", idIdMessageResources);
        }
        return MapUtils.EMPTY_MAP;
    }

    *//**
     * 从缓存中取出国际化配置对应的数据 或者从父级获取
     *
     * @param code
     * @param locale
     * @return
     *//*
    public String getSourceFromCache(String code, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        } else {
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(code, null, locale);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            return code;
        }
    }

    // 下面三个重写的方法是比较重要的
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader == null ? new DefaultResourceLoader() : resourceLoader);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getSourceFromCache(code, locale);
        MessageFormat messageFormat = new MessageFormat(msg, locale);
        return messageFormat;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }


}*/
