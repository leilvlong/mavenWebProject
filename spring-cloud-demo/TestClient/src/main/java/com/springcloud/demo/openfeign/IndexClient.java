package com.springcloud.demo.openfeign;

import com.springcloud.demo.pojo.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 分布式服务调用
 */

@FeignClient(value = "indexClient", fallback = IndexClient.IndexClientFallback.class)
public interface IndexClient {

    @RequestMapping(value="/index/client")
    Response index();

    @Component
    static class IndexClientFallback implements IndexClient {

        @Override
        public Response index() {
            return new Response("服务调用异常,触发熔断器",500 );
        }
    }

}
