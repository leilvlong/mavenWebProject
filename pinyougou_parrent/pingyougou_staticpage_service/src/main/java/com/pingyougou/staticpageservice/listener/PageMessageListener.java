package com.pingyougou.staticpageservice.listener;

import com.alibaba.fastjson.JSON;
import com.pingyougou.pojos.MessageInfo;
import com.pingyougou.pojos.TbItem;

import com.pingyougou.staticpageservice.TbItemPageHtml;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageMessageListener implements MessageListenerConcurrently {
    @Autowired
    private TbItemPageHtml tbItemPageHtml;


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.println("执行消息队列");

        try {
            for (MessageExt messageExt : list) {
                byte[] body = messageExt.getBody();
                MessageInfo messageInfo = JSON.parseObject(new String(body), MessageInfo.class);
                switch (messageInfo.getMethod()){
                    case MessageInfo.METHOD_ADD:{
                        updatePageHtml(messageInfo);
                        System.out.println("添加成功");
                        break;
                    }
                    case MessageInfo.METHOD_UPDATE:{
                        updatePageHtml(messageInfo);
                        System.out.println("更新成功");
                        break;
                    }
                    case MessageInfo.METHOD_DELETE:{
                        String context = messageInfo.getContext().toString();
                        Long[] ids = JSON.parseObject(context, Long[].class);
                        for (Long id : ids) {
                            new File("F:\\freemarker\\"+id+".html").delete();
                        }
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

    private void updatePageHtml(MessageInfo info) {
        String context1 = info.getContext().toString();//获取到的是Map对象 并不能直接序列化回来 需要直接转成字符串
        List<TbItem> tbItems = JSON.parseArray(context1, TbItem.class);
        Set<Long> set = new HashSet<>();
        for (TbItem tbItem : tbItems) {
            //循环遍历进行生成静态页面。
            set.add(tbItem.getGoodsId());
        }
        //循环遍历 生成静态页面
        for (Long aLong : set) {
            tbItemPageHtml.genItemHtml(aLong);
        }
        return;
    }
}
