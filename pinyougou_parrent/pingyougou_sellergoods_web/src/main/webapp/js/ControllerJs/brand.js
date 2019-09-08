var app = new Vue({
    el: "#app",
    data: {
        list: [],
        pages: 0,
        pageNum: 1,
        entry: {},
        ids: [],
        searchEntity: {}
    },
    methods: {
        findAll: function () {
            axios.get('/brand/all.shtml').then(function (response) {
                app.list = response.data;
            }).catch(function (error) {
            })
        },

        searchList: function (curPage) {
            axios.post('/brand/search.shtml?pageNum=' + curPage,this.searchEntity).then(function (response) {
                app.list = response.data.list;
                app.pages = response.data.pages;
                app.pageNum = response.data.pageNum;
            })
        },

        add: function () {
            axios.post('/brand/add.shtml', this.entry).then(function (response) {
                if (response.data.success) {
                    app.entry = {};
                    app.searchList(1)
                }
            })
        },

        findOne: function (id) {
            axios.post('/brand/findone/' + id + '.shtml', this.entry).then(function (response) {
                app.entry = response.data
            })
        },

        update: function () {
            axios.post('/brand/update.shtml', this.entry).then(function (response) {
                if (response.data.success) {
                    app.entry = {};
                    app.searchList(1);
                }
            })
        },
        save: function () {
            if (this.entry.id != null) {
                this.update();
            } else {
                this.add();
            }
        },

        dele: function () {
            axios.post('/brand/delete.shtml', this.ids).then(function (response) {
                if (response.data.success) {
                    app.searchList(1);
                }
            })
        }


    },
    created: function () {
        this.searchList(1)
    }


});