package com.springboot.example.druibandmybatis.configs;


import com.springboot.example.druibandmybatis.Dao.TextResource;
import com.springboot.example.druibandmybatis.pojo.I18n;
import com.springboot.example.druibandmybatis.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @功能说明：自定义Message类
 * @创建人员：Chase Wang
 * @变更记录： 1、2018年09月26日 Chase Wang Create
 */
@Component("messageResource")
public class MessageResource extends ResourceBundleMessageSource implements ResourceLoaderAware, InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TextResource userDao;

    //map切分字符
    protected static final String MAP_SPLIT_CODE = "|";

    private ResourceLoader resourceLoader;

    public MessageResource() {
    }

    private static Map<String, String> dataMap = new ConcurrentHashMap<>();

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataMap.clear();
        dataMap.putAll(LoadDesc());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (null != resourceLoader ? resourceLoader : new DefaultResourceLoader());
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }

    /**
     * @创建日期: 2018/9/26
     * @功能说明: 获取数据库map集合
     * @参数说明:
     * @返回说明: 返回语言map集合
     */
    public Map<String, String> LoadDesc() {
        List<I18n> textInfo = null;
        try {
            textInfo = userDao.findAll();
        } catch (Exception e) {
            logger.error("MessageResoure LoadDesc error:" + e.getMessage());
        }
        return object2Map(textInfo);
    }

    /**
     * 数据转换
     *
     * @param textInfo
     * @return
     */
    public Map<String, String> object2Map(List<I18n> textInfo) {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (I18n resource : textInfo) {
            if (StringUtils.isNotEmpty(resource.getEnUS())) {
                map.put(resource.getTextCode() + MAP_SPLIT_CODE + "en", resource.getEnUS());
            }
            if (StringUtils.isNotEmpty(resource.getZhCN())) {
                map.put(resource.getTextCode() + MAP_SPLIT_CODE + "zh", resource.getZhCN());
            }
        }
        return map;
    }


    /**
     * @创建日期: 2018/9/26
     * @功能说明: 根据 code  获取 内容
     * @参数说明:
     * @返回说明:
     */
    public String getText(String code, Locale locale) {
        String localeCode = locale.getLanguage();
        String key = code + MAP_SPLIT_CODE + localeCode;
        String localeText = dataMap.get(key);
        String resourceText = code;

        if(localeText != null) {
            resourceText = localeText;
        }
        else {
            localeCode = Locale.ENGLISH.getLanguage();
            key = code + MAP_SPLIT_CODE + localeCode;
            localeText = dataMap.get(key);
            if(localeText != null) {
                resourceText = localeText;
            }
            else {
                try {
                    if(getParentMessageSource() != null) {
                        resourceText = getParentMessageSource().getMessage(code, null, locale);
                    }
                } catch (Exception e) {
                    logger.error("Cannot find message with code: " + code);
                }
            }
        }
        return resourceText;

    }


}
