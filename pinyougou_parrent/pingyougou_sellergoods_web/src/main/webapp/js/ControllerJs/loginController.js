var app = new Vue({

    el: "#app",
    data: {
       username: ""
    },
    methods: {

        getUserName:function () {
            axios.get("/login/getUserName.shtml").then(function (response) {
                app.username = response.data
            })
        }

    },
    created: function () {
        this.getUserName()
    }



});