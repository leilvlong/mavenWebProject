var app = new Vue({
    el: "#app",
    data: {
        list: [],
        astatus: 0,
        amessage: ''
    },
    methods: {
        findAll:function () {
            axios.get('/test/hello').then(function (response) {
                alert("发送请求成功");
                app.amessage = response.data.amessage;
                app.astatus=response.data.astatus;
                app.list = response.data.data;

            });
        }
    },
    //钩子函数 初始化了事件和
    created: function () {
        this.findAll()
    }

});
