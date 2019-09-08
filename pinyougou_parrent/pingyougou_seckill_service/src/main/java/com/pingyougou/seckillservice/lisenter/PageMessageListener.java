package com.pingyougou.seckillservice.lisenter;

import com.alibaba.fastjson.JSON;
import com.pingyougou.mapper.TbSeckillGoodsMapper;
import com.pingyougou.pojos.MessageInfo;
import com.pingyougou.pojos.TbSeckillGoods;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监听器  目的就是监听秒杀商品的ID 生成静态页面
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.seckill.listener *
 * @since 1.0
 */
public class PageMessageListener implements MessageListenerConcurrently {


    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private TbSeckillGoodsMapper goodsMapper;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (msgs != null) {
            for (MessageExt msg : msgs) {
                //1.获取消息体
                byte[] body = msg.getBody();//
                //2.转成STRING
                String s = new String(body);
                //3.转成JSON对象 Messageinfo
                MessageInfo messageInfo = JSON.parseObject(s, MessageInfo.class);
                //4.判断是否是ADD ---生成静态页面
                switch (messageInfo.getMethod()) {
                    case 1: //add
                    {
                        // 获取对象
                        String context1 = messageInfo.getContext().toString();
                        //转成long[] 数组
                        Long[] longs = JSON.parseObject(context1, Long[].class);
                        //查询数据库的数据
                        for (Long aLong : longs) {
                            //使用freemarker 生成静态页
                            genHTML("item.ftl", aLong);
                        }
                        break;
                    }
                    case 2://update
                    {
                        break;
                    }
                    case 3:// delete
                    {

                        break;
                    }
                    default: {
                        break;
                    }

                }

            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Value("${PageDir}")
    private String pageDir;

    //seckillGoods
    //生成静态页面  数据集 + 模板  =html
    private void genHTML(String templateName, Long id) {
        FileWriter writer = null;
        try {
            //1.创建一个配置类Configruation
            //2.设置utf-8编码
            //3.设置模板文件所在的目录
            Configuration configuration = configurer.getConfiguration();
            //4.创建模板文件(模板语法 ----秒杀详情页面的模板)

            //5.加载模板对象
            Template template = configuration.getTemplate(templateName);

            //6.从数据库中获取数据集(秒杀商品的数据)
            TbSeckillGoods tbSeckillGoods = goodsMapper.selectByPrimaryKey(id);

            Map<String, Object> model = new HashMap<>();
            model.put("seckillGoods", tbSeckillGoods);

            //7.创建一个writer
            writer = new FileWriter(new File(pageDir + id + ".html"));
            //8.处理生成页面
            template.process(model, writer);

            //9.关闭流
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
