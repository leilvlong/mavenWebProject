var app = new Vue({
    el: "#app",
    data: {
        pages:15,
        pageNo:1,
        list:[],
        itemCatList:[],//格式 是  [null,null,n......,"手机"]
        entity:{},
        status:['未审核','已经审核','审核未通过','已经关闭'],
        ids:[],
        searchEntity:{}
    },
    methods: {
        searchList:function (curPage) {
            axios.post('/goods/search.shtml?pageNo='+curPage,this.searchEntity).then(function (response) {
                //获取数据
                app.list=response.data.list;

                //当前页
                app.pageNo=curPage;
                //总页数
                app.pages=response.data.pages;
            });
        },
        //查询所有品牌列表
        findAll:function () {
            console.log(app);
            axios.get('/goods/findAll.shtml').then(function (response) {
                console.log(response);
                //注意：this 在axios中就不再是 vue实例了。
                app.list=response.data;

            }).catch(function (error) {

            })
        },
        findPage:function () {
            var that = this;
            axios.get('/goods/findPage.shtml',{params:{
                    pageNo:this.pageNo
                }}).then(function (response) {
                console.log(app);
                //注意：this 在axios中就不再是 vue实例了。
                app.list=response.data.list;
                app.pageNo=curPage;
                //总页数
                app.pages=response.data.pages;
            }).catch(function (error) {

            })
        },
        //该方法只要不在生命周期的
        add:function () {
            axios.post('/goods/add.shtml',this.entity).then(function (response) {
                console.log(response);
                if(response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        update:function () {
            axios.post('/goods/update.shtml',this.entity).then(function (response) {
                console.log(response);
                if(response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        save:function () {
            if(this.entity.id!=null){
                this.update();
            }else{
                this.add();
            }
        },
        findOne:function (id) {
            axios.get('/goods/findOne/'+id+'.shtml').then(function (response) {
                app.entity=response.data;
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        dele:function () {
            axios.post('/goods/delete.shtml',this.ids).then(function (response) {
                console.log(response);
                if(response.data.success){
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        //查询所有的分类的数据
        findAllItemCategory:function () {
            axios.get('/itemCat/findAll.shtml').then(
                function (response) {//response.data=[{id:378,name:手机}，{},{}]
                    //itemCatList:[],//格式 是  [null,null,n......,"手机"]
                    //itemCatList[0]="afaaa";

                    for(var i=0;i<response.data.length;i++){
                        app.itemCatList[response.data[i].id]=response.data[i].name;
                    }

                    //重新手动渲染
                    app.$mount("#app");
                }
            )
        }



    },
    //钩子函数 初始化了事件和
    created: function () {
        this.findAllItemCategory();
        this.searchList(1);

    }

})
