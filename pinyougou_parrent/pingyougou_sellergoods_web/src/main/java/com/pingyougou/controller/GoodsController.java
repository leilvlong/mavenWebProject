package com.pingyougou.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pingyougou.pojos.MessageInfo;
import com.pingyougou.pojos.TbGoods;
import com.pingyougou.pojos.TbItem;
import com.pingyougou.service.GoodsService;
import entity.Goods;
import entity.Result;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;



	@Autowired
	private DefaultMQProducer defaultMQProducer;
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	
	@RequestMapping("/findPage")
    public PageInfo<TbGoods> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize) {
        return goodsService.findPage(pageNo, pageSize);
    }
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		try {
			//获取登录的用户(商家)的id
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			goods.getGoods().setSellerId(sellerId);
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	public Goods findOne(@PathVariable(value = "id") Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(@RequestBody Long[] ids){
		try {
			goodsService.delete(ids);
            MessageInfo messageInfo = new MessageInfo("Goods_Topic",
                    "goods_delete_tag",
                    "delete",
                    ids,
                    MessageInfo.METHOD_DELETE);
            Message msg = new Message(
                    messageInfo.getTopic(),
                    messageInfo.getTags(),
                    messageInfo.getKeys(),
                    JSON.toJSONString(messageInfo).getBytes());
            SendResult send = defaultMQProducer.send(msg);
            System.out.println("发送状态: "+ send.getSendStatus());
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	

	@RequestMapping("/search")
    public PageInfo<TbGoods> findPage(@RequestParam(value = "pageNo", defaultValue = "1", required = true) Integer pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize,
                                      @RequestBody TbGoods goods) {

        return goodsService.findPage(pageNo, pageSize, goods);
    }

    @RequestMapping("/updateStatus")
	public Result updateStatus(@RequestBody Long[] ids,String status){
		try {
			goodsService.updateStatus(ids,status);
			if("1".equals(status)){
				List<TbItem> tbItemListByIds = goodsService.findTbItemListByIds(ids);
                System.out.println(tbItemListByIds);
				MessageInfo messageInfo = new MessageInfo("Goods_Topic",
                        "goods_update_tag",
                        "updateStatus",
                        tbItemListByIds,
                        MessageInfo.METHOD_UPDATE);
				Message msg = new Message(
				        messageInfo.getTopic(),
                        messageInfo.getTags(),
                        messageInfo.getKeys(),
                        JSON.toJSONString(messageInfo).getBytes());
                SendResult send = defaultMQProducer.send(msg);
                System.out.println("发送状态: "+ send.getSendStatus());
            }
			return new Result(true,"更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"更新失败");
		}
	}
	
}
