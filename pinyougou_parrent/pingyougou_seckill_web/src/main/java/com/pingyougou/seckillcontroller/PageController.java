package com.pingyougou.seckillcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.seckill.controller *
 * @since 1.0
 */
@Controller
public class PageController {

    @RequestMapping("/page/login")
    public String showPage(String url){
        //重定向
        return "redirect:"+url;
    }
}
