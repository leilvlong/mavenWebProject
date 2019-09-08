var app = new Vue({
    el: "#app",
    data: {
        totalMoney:0,
        out_trade_no:''
    },
    methods: {

    },
    created: function () {
        var jsonObj = this.getUrlParam();
        this.totalMoney=decodeURIComponent(jsonObj.money);
    }
});