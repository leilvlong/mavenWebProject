﻿var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        entity:{},
        ids:[],
        searchEntity:{}
    },
    methods: {

        //查询所有品牌列表
        findAll:function () {
            alert("???")
            axios.get('/seckillGoods/findAll.shtml').then(function (response) {

                //注意：this 在axios中就不再是 vue实例了。
                app.list=response.data;
                alert("???")
            }).catch(function (error) {

            })
        }




    },
    //钩子函数 初始化了事件和
    created: function () {
      
       this.findAll();

    }

})
