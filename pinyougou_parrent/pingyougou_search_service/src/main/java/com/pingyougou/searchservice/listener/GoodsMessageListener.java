package com.pingyougou.searchservice.listener;

import com.alibaba.fastjson.JSON;
import com.pingyougou.pojos.MessageInfo;
import com.pingyougou.pojos.TbItem;
import com.pingyougou.searchservice.impl.TbItemSearchServiceImpl;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class GoodsMessageListener implements MessageListenerConcurrently {
    @Autowired
    private TbItemSearchServiceImpl tbItemSearchServiceImpl;


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.println("执行消息队列");

        try {
            for (MessageExt messageExt : list) {
                byte[] body = messageExt.getBody();
                MessageInfo messageInfo = JSON.parseObject(new String(body), MessageInfo.class);
                String context = messageInfo.getContext().toString();
                switch (messageInfo.getMethod()){
                    case MessageInfo.METHOD_ADD:{
                        List<TbItem> tbItems = JSON.parseArray(context, TbItem.class);
                        tbItemSearchServiceImpl.updateIndex(tbItems);
                        System.out.println("添加成功");
                        break;
                    }

                    case MessageInfo.METHOD_UPDATE:{
                        List<TbItem> tbItems = JSON.parseArray(context, TbItem.class);
                        tbItemSearchServiceImpl.updateIndex(tbItems);
                        System.out.println("更新成功");
                        break;
                    }

                    case MessageInfo.METHOD_DELETE:{
                        Long[] ids = JSON.parseObject(context, Long[].class);
                        System.out.println(Arrays.toString(ids));
                        tbItemSearchServiceImpl.deleteByIds(ids);
                        System.out.println("删除成功");
                        break;
                    }

                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }


    }
}
