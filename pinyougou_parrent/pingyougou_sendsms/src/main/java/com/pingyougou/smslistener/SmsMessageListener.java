package com.pingyougou.smslistener;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.pingyougou.smsutil.SmsUtil;
import com.sun.nio.sctp.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Map;

public class SmsMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.println("执行发送短信");
        try{
            for (MessageExt messageExt : list) {
                String body = new String(messageExt.getBody());
                System.out.println(body);
                Map<String,String> map = JSON.parseObject(body, Map.class);
                SendSmsResponse sendSmsResponse = SmsUtil.sendSms(map);
                System.out.println(sendSmsResponse.getCode());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

    }
}
