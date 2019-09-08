﻿var app = new Vue({
    el: "#app",
    data: {
        pages: 15,
        pageNo: 1,
        list: [],
        imageurl: '',
        image_entity: {color: '', url: ''},
        entity: {
            goods: {},
            goodsDesc: {itemImages: [], customAttributeItems: [], specificationItems: []},
            itemList: []
        },
        ids: [],
        searchEntity: {},
        itemCat1List: [],
        itemCat2List: [],
        itemCat3List: [],
        brandTextList: [],
        specList: [],

    },
    methods: {
        searchList: function (curPage) {
            axios.post('/goods/search.shtml?pageNo=' + curPage, this.searchEntity).then(function (response) {
                //获取数据
                app.list = response.data.list;

                //当前页
                app.pageNo = curPage;
                //总页数
                app.pages = response.data.pages;
            });
        },

        //查询所有品牌列表
        findAll: function () {
            console.log(app);
            axios.get('/goods/findAll.shtml').then(function (response) {
                console.log(response);
                //注意：this 在axios中就不再是 vue实例了。
                app.list = response.data;

            }).catch(function (error) {

            })
        },

        findPage: function () {
            var that = this;
            axios.get('/goods/findPage.shtml', {
                params: {
                    pageNo: this.pageNo
                }
            }).then(function (response) {
                console.log(app);
                //注意：this 在axios中就不再是 vue实例了。
                app.list = response.data.list;
                app.pageNo = curPage;
                //总页数
                app.pages = response.data.pages;
            }).catch(function (error) {

            })
        },

        //该方法只要不在生命周期的
        add:function () {
            //1.获取富文本编辑器中的html的值 赋值给entity变量中的介绍属性中
            this.entity.goodsDesc.introduction=editor.html();
            axios.post('/goods/add.shtml',this.entity).then(function (response) {
                if(response.data.success){
                    window.location.href="goods.html";
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        update:function () {
            this.entity.goodsDesc.introduction=editor.html();
            axios.post('/goods/update.shtml',this.entity).then(function (response) {
                console.log(response);
                if(response.data.success){
                    window.location.href="goods.html";
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        save:function () {
            if(this.entity.goods.id!=null){
                this.update();
            }else{
                this.add();
            }

        },

        dele: function () {
            axios.post('/goods/delete.shtml', this.ids).then(function (response) {
                console.log(response);
                if (response.data.success) {
                    app.searchList(1);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        //文件上传
        //1.模拟表单 设置数据
        //2.发送aajx请求 上传图片
        uploadFile: function () {
            //模拟创建一个表单对象
            var formData = new FormData();
            //参数formData.append('file' 中的file 为表单的参数名  必须和 后台的file一致
            //file.files[0]  中的file 指定的时候页面中的input="file"的id的值 files 指定的是选中的图片所在的文件对象数组，这里只有一个就选中[0]
            formData.append('file', file.files[0]);
            axios({
                url: 'http://localhost:9110/upload/uploadFile.shtml',
                //data就是表单数据
                data: formData,
                method: 'post',
                //指定头信息：
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                //开启跨域请求携带相关认证信息
                withCredentials: true
            }).then(function (response) {
                if (response.data.success) {
                    app.image_entity.url = response.data.message;
                    ;
                } else {
                    alert(response.data.message);
                }
            })

        },

        addImage_Entity: function () {
            //向数组中添加一个图片的对象
            this.entity.goodsDesc.itemImages.push(this.image_entity);
        },

        findCatType: function () {
            axios.post('/itemCat/findByParrent/0.shtml').then(function (response) {
                app.itemCat1List = response.data
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        updateChecked: function (event, specName, specValue) {
            //1 如果有对象
            var searchObject = this.searchObjectByKey(this.entity.goodsDesc.specificationItems, specName, 'attributeName');//"网络"
            if (searchObject != null) {
                //判断 是否是勾选 如果是勾选 添加数据
                if (event.target.checked) {
                    //规格选的的值 添加到对象中 的attributeValue中
                    searchObject.attributeValue.push(specValue);
                } else {
                    //否则 就是移除数据
                    searchObject.attributeValue.splice(searchObject.attributeValue.indexOf(specValue), 1);
                    if (searchObject.attributeValue.length == 0) {
                        this.entity.goodsDesc.specificationItems.splice(this.entity.goodsDesc.specificationItems.indexOf(searchObject), 1);
                    }
                }
            } else {
                //2. 如果没有对象
                //直接添加对象即可
                this.entity.goodsDesc.specificationItems.push(
                    {"attributeValue": [specValue], "attributeName": specName})
            }
        },

        searchObjectByKey: function (list, specName, key) {
            for (var i = 0; i < list.length; i++) {
                var obj = list[i];//{"attributeValue":["移动3G","移动4G"],"attributeName":"网络"}
                if (specName == obj[key]) {
                    return obj;
                }
            }
            return null;
        },

        createList: function () {
            //1.初始化
            this.entity.itemList = [{'price': 0, 'num': 0, 'status': '0', 'isDefault': '0', spec: {}}];

            //2.循环遍历entity.goodsDesc.specificationItems:
            //[{"attributeValue":["移动3G","移动4G"],"attributeName":"网络"}]
            var specificationItemsObject = this.entity.goodsDesc.specificationItems;
            for (var i = 0; i < specificationItemsObject.length; i++) {
                var obj = specificationItemsObject[i];//  {"attributeValue":["移动3G","移动4G"],"attributeName":"网络"}
                //3.拼接要的格式
                this.entity.itemList = this.addColumn(this.entity.itemList, obj.attributeName, obj.attributeValue);
            }
        },

        addColumn: function (list, columnName, columnValue) {
            var newList = [];
            for (var i = 0; i < list.length; i++) {
                var oldRow = list[i];// {'price':0,'num':0,'status':'0','isDefault':'0',spec:{}}
                for (var j = 0; j < columnValue.length; j++) {
                    var newRow = JSON.parse(JSON.stringify(oldRow));
                    newRow.spec[columnName] = columnValue[j];//移动3G  深克隆     {'price':0,'num':0,'status':'0','isDefault':'0',spec:{"网络":"移动3G"}}
                    newList.push(newRow);
                }
            }
            return newList;
        },

        findOne:function (id) {
            axios.get('/goods/findOne/'+id+'.shtml').then(function (response) {
                app.entity=response.data;
                //设置 商品的介绍给富文本编辑器
                editor.html(app.entity.goodsDesc.introduction);
                //转JSON
                app.entity.goodsDesc.itemImages=JSON.parse(app.entity.goodsDesc.itemImages);

                app.entity.goodsDesc.customAttributeItems=JSON.parse(app.entity.goodsDesc.customAttributeItems);

                app.entity.goodsDesc.specificationItems=JSON.parse(app.entity.goodsDesc.specificationItems);

                //获取sku的列表 [{spec:{}},{}]

                for(var i=0;i<app.entity.itemList.length;i++){
                    var obj = app.entity.itemList[i];// {spec:{}}
                    obj.spec=JSON.parse(obj.spec);
                }




            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        /**
         *
         * @param specName 网络
         * @param specValue 移动3G
         * @returns {boolean}
         */
        isChecked:function (specName,specValue) {
            //1.根据规格的名称 从 变量中查询是否有对象
            var searchObj = this.searchObjectByKey(this.entity.goodsDesc.specificationItems,specName,'attributeName');
            //2.如 有对象 再查询 是否匹配规格的选项值 ，如果匹配 上 true,否则 false
            if(searchObj!=null){
                if(searchObj.attributeValue.indexOf(specValue)!=-1){
                    return true;
                }
            }
            return false;
        },
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


    watch: {
        'entity.goods.category1Id': function (newval, oldvalue) {
            axios.get('/itemCat/findByParrent/' + newval + '.shtml').then(function (response) {
                app.itemCat2List = response.data
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        'entity.goods.category2Id': function (newval, oldvalue) {
            axios.get('/itemCat/findByParrent/' + newval + '.shtml').then(function (response) {
                app.itemCat3List = response.data
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        'entity.goods.category3Id': function (newval, oldvalue) {
            axios.get('/itemCat/findOne/' + newval + '.shtml').then(function (response) {
                app.$set(app.entity.goods, 'typeTemplateId', response.data.typeId);
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        'entity.goods.typeTemplateId': function (newval, oldvalue) {
            axios.get('/typeTemplate/findOne/' + newval + '.shtml').then(function (response) {
                var typeTemplate = response.data;
                app.brandTextList=JSON.parse(typeTemplate.brandIds);//[{id:1,text:'三星'},{}]
                if(app.entity.goods.id==null || app.entity.goods.id==undefined){
                    app.entity.goodsDesc.customAttributeItems= JSON.parse(typeTemplate.customAttributeItems);
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });

            axios.get('/typeTemplate/findSpecList/' + newval + '.shtml').then(
                function (response) {
                    alert("??????")
                    app.specList = response.data;
                    var data = response.data;
                    alert(data[0].optionList[0].optionName)

                    for(i=0;i<app.specList.optionList;i++){
                        alert()
                    }


                }
            )


        },


    },

//钩子函数 初始化了事件和
    created: function () {
        this.findCatType();
        var request = this.getUrlParam();
        console.log(request);
        //根据ID 获取 商品的信息
        if (request.id != null && request.id != undefined) {
            this.findOne(request.id);
        }
    }

});
