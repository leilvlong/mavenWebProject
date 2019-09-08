package com.pingyougou.userservice.impl;
import java.util.*;

import com.pingyougou.core.service.CoreServiceImpl;
import com.pingyougou.mapper.TbUserMapper;
import com.pingyougou.pojos.TbUser;

import com.pingyougou.userservice.UserService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;





/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl extends CoreServiceImpl<TbUser> implements UserService {

	
	private TbUserMapper userMapper;

	@Autowired
	public UserServiceImpl(TbUserMapper userMapper) {
		super(userMapper, TbUser.class);
		this.userMapper=userMapper;
	}


    @Autowired
	private RedisTemplate redisTemplate;

	@Value("${sign_name}")
	private String sign_name;
	@Value("${template_code}")
	private String template_code;

	@Autowired
	private DefaultMQProducer producer;

	@Override
	public void createSmsCode(String phone) {
		//1.生成6位数字 999999
		// (Math.random() * 9 + 1) <10
		// 100000 *10

        String code = String.valueOf((long) ((Math.random() * 9 + 1) * 100000));

        //2.存储到redis中  集成redis  : 1.依赖  2.配置文件 3.注入resdisTemplate
        redisTemplate.boundValueOps("ZHUCE"+phone).set(code);

        //3.组装消息对象,  手机号  签名 模板  验证码
        Map<String, String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("sign_name",sign_name);
        map.put("template_code",template_code);
        map.put("code","{\"code\":\""+code+"\"}");
        //4 .发送消息  1.依赖  2. 配置文件 3. 注入producer 4.发送消息
        try {
            Message message = new Message(
                    "SMS_TOPIC",
                    "SEND_MESSAGE_TAG",
                    "createCode",
                    JSON.toJSONString(map).getBytes()
            );

            SendResult send = producer.send(message);
            System.out.println(send);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

	@Override
	public boolean checkSmsCode(String phone, String smscode) {
		if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(smscode)){
			return false;
		}
		String code = (String) redisTemplate.boundValueOps("ZHUCE" + phone).get();

		if(!smscode.equals(code)){
			return false;
		}
		return true;
	}


	@Override
	public void add(TbUser record) {
		//1.md5 加密存储密码
		String password = record.getPassword();//明文
		String passwordencode = DigestUtils.md5DigestAsHex(password.getBytes());//密码文
		record.setPassword(passwordencode);
		//2.补充必要字段属性值
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());

		//3.添加数据到数据库中
		userMapper.insert(record);
	}
}
