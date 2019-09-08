package com.pingyougou.searchcontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pingyougou.searchservice.TbItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/itemSearch")
public class SearchController {

    @Reference
    private TbItemSearchService tbItemSearchService;


    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map<String,Object> searchMap){
        System.out.println(searchMap);
        if (tbItemSearchService == null){
            System.out.println("????");
            return null;
        }
        return  tbItemSearchService.search(searchMap);
    }
}
