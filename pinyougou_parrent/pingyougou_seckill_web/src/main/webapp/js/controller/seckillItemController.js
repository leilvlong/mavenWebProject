var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        ids:[],
        seckillId:0,//绑定当前的商品的ID的值
        searchEntity:{}
    },
    methods: {

        //方法当点击立即抢购的时候调用
        submitOrder:function () {
            console.log("下单的id的值为:"+this.seckillId);
            axios.get('/seckillOrder/submitOrder.shtml?id='+this.seckillId).then(
                function (response) {//response.data=result
                    if(response.data.success){
                        //跳转到支付页面
                        alert("去支付");
                    }else{
                        if(response.data.message=='403'){
                            //要去登录
                            alert("要登录");
                            var url = window.location.href;//获取当前浏览器中的URL的地址
                            window.location.href="http://localhost:9109/page/login.shtml?url="+url;
                        }else{
                            alert(response.data.message);
                        }
                    }
                }
            )
        }

    },

    created: function () {
        //页面加载的时候从URL中解析出ID 的值 赋值给变量seckillId
        let urlParam = this.getUrlParam();
        this.seckillId=urlParam.id;
    }

})
