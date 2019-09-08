package com.pingyougou.payinterface.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pingyougou.payinterface.WeixinPayService;
import com.pingyougou.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    @Override
    public Map<String, String> createNative(String out_trade_no, String total_fee) {
        try {
            //1.组合参数集 存储到map中 map转换成XML

            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("appid","wx8397f8696b538317");
            paramMap.put("mch_id","1473426802");
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("body","品优购");
            paramMap.put("out_trade_no",out_trade_no);
            paramMap.put("total_fee",total_fee);
            paramMap.put("spbill_create_ip","127.0.0.1");
            paramMap.put("notify_url","http://a31ef7db.ngrok.io/WeChatPay/WeChatPayNotify");
            paramMap.put("trade_type","NATIVE");

            //......
            //自动添加签名 而且转成字符串
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");


            //2.使用httpclient 调用 接口 发送请求

            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);//请求体
            httpClient.post();
            //3.获取结果 XML  转成MAP(code_url)
            String resultXml = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(resultXml);

            Map<String,String> resultMap = new HashMap<>();

            resultMap.put("code_url",map.get("code_url"));
            resultMap.put("out_trade_no",out_trade_no);
            resultMap.put("total_fee",total_fee);
            //4.返回map
            System.out.println("333");
            return resultMap;
        } catch (Exception e) {
            System.out.println("444");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map<String, String> queryStatus(String out_trade_no) {
        try {
            //1.组合参数集 存储到map中 map转换成XML

            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("appid","wx8397f8696b538317");
            paramMap.put("mch_id","1473426802");
            paramMap.put("nonce_str",WXPayUtil.generateNonceStr());
            paramMap.put("out_trade_no",out_trade_no);

            //自动添加签名 而且转成字符串
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");


            //2.使用httpclient 调用 接口 发送请求

            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);//请求体
            httpClient.post();
            //3.获取结果 XML  转成MAP(code_url)
            String resultXml = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> closePay(String out_trade_no) {
        try {
            //1.组合参数集 存储到map中 map转换成XML

            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("appid","wx8397f8696b538317");
            paramMap.put("mch_id","1473426802");
            paramMap.put("nonce_str",WXPayUtil.generateNonceStr());
            paramMap.put("out_trade_no",out_trade_no);

            //自动添加签名 而且转成字符串
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");


            //2.使用httpclient 调用 接口 发送请求

            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);//请求体
            httpClient.post();
            //3.获取结果 XML  转成MAP(code_url)
            String resultXml = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
