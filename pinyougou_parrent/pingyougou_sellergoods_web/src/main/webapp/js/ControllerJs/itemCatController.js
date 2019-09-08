var app = new Vue({
    el: "#app",
    data: {
        pages: 15,
        pageNo: 1,
        list: [],
        entity: {parentId: 0},
        ids: {grade: 1, id: []},
        searchEntity: {},
        entity_1: {},
        entity_2: {},
        grade: 1
    },
    methods: {
        searchList: function (curPage) {
            axios.post('/itemCat/search.shtml?pageNo=' + curPage, this.searchEntity).then(function (response) {
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
            axios.get('/itemCat/findAll.shtml').then(function (response) {
                console.log(response);
                //注意：this 在axios中就不再是 vue实例了。
                app.list = response.data;

            }).catch(function (error) {

            })
        },
        findPage: function () {
            var that = this;
            axios.get('/itemCat/findPage.shtml', {
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

        add: function () {
            alert(this.entity.parentId)
            axios.post('/itemCat/add.shtml', this.entity).then(function (response) {
                console.log(response);
                if (response.data.success) {
                    window.location.reload()
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        update: function () {
            alert(this.entity.parentId)
            axios.post('/itemCat/update.shtml', this.entity).then(function (response) {
                console.log(response);
                if (response.data.success) {
                    window.location.reload()
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        save: function () {
            if (this.entity.id != null) {
                this.update();
            } else {
                this.add();
            }
        },
        findOne: function (id) {
            alert(id)
            axios.get('/itemCat/findOne/' + id + '.shtml').then(function (response) {
                app.entity = response.data;
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        dele: function () {
            axios.post('/itemCat/delete.shtml', this.ids).then(function (response) {
                if (response.data.success) {
                    window.location.reload()
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },
        findByParrentId: function (parrentId) {
            axios.post('/itemCat/findByParrent/' + parrentId + '.shtml', this.ids).then(function (response) {
                app.list = response.data;
                app.entity.parentId = parrentId;
            }).catch(function (error) {
                console.log("1231312131321");
            });
        },

        graderList: function (p_entity) {
            if (this.grade == 1) {
                this.entity_1 = {};
                this.entity_2 = {}
            } else if (this.grade == 2) {
                this.entity_1 = p_entity;
                this.entity_2 = {}
            } else if (this.grade == 3) {
                this.entity_2 = p_entity;
            }
            this.findByParrentId(p_entity.id)
        }


    },
    //钩子函数 初始化了事件和
    created: function () {
        this.findByParrentId(0)

    }

})
