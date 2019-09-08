var app = new Vue({
    el: "#app",
    data: {
        pages: 15,
        pageNo: 1,
        address: {},//当前地址对象
        addressList: [],//地址列表
        totalMoney: 0,//总金额
        totalNum: 0,//总数量
        cartList: [],
        order: {paymentType: '1'},//订单对象
        ids: [],
        searchEntity: {},
        entity: {},
        selected: [],
        selectSubed: []
    },
    methods: {
        //查询所有的购物车的列表数据
        findCartList: function () {
            axios.get('/cart/findCartList.shtml').then(
                function (response) {
                    app.cartList = response.data;//List<Cart>   cart { List<ORDERiMTE> }
                    app.totalMoney = 0;
                    app.totalNum = 0;
                    for (var i = 0; i < response.data.length; i++) {
                        var obj = response.data[i];//Cart
                        for (var n = 0; n < obj.orderItemList.length; n++) {
                            var objx = obj.orderItemList[n];//ORDERiMTE
                            app.totalMoney += objx.totalFee;
                            app.totalNum += objx.num;
                        }
                    }

                }
            )
        },
        //向已有的购物车中添加商品
        addGoodsToCartList: function (itemId, num) {
            axios.get('/cart/addGoodsToCartList.shtml?itemId=' + itemId + '&num=' + num).then(
                function (response) {
                    if (response.data.success) {
                        //
                        app.findCartList();
                    }
                }
            )
        },
        //选择地址 点击联系人的时候调用 方法 改变 变量address的值
        selectAddress: function (address) {
            this.address = address;
        },
        isSelected: function (address) {
            //判断是否要显示
            if (this.address == address) {
                return true;
            }
            return false;
        },
        //获取登录用户的地址列表展示
        findAddressList: function () {
            axios.get('/address/findAddressListByUserId.shtml').then(
                function (response) {//response.data=list<address>
                    app.addressList = response.data;

                    for (var i = 0; i < app.addressList.length; i++) {
                        //{ "address": "你猜", "alias": "家里", "cityId": null, "contact": "尼采", "createDate": null, "id": 65, "isDefault": "1", "mobile": "13888888888", "notes": null, "provinceId": null, "townId": null, "userId": "zhangsanfeng" }
                        var obj = app.addressList[i];//
                        if (obj.isDefault == '1') {
                            app.address = obj;
                            break;
                        }
                    }
                }
            )
        },
        selectType: function (type) {
            this.order.paymentType = type;
        },


        //方法 当点击提交订单的时候调用
        submitOrder: function () {
            //先获取地址的信息 赋值给变量order

            this.order.receiverAreaName = this.address.address;//详细地址
            this.order.receiverMobile = this.address.mobile;//电话
            this.order.receiver = this.address.contact;//联系人

            axios.post('/order/submitOrder.shtml', this.order).then(
                function (response) {//response.data=result
                    if (response.data.success) {
                        //跳转到支付的页面
                        window.location.href = "pay.html";
                    }
                }
            )
        },

        selectAddCart:function (cart) {
            var indexOf = this.selected.indexOf(cart.sellerId);
            if (indexOf != -1){
                this.selected.splice(indexOf,1);
                cart.orderItemList.forEach(function (orderItem) {
                    var of = app.selected.indexOf(orderItem.itemId);
                    app.selected.splice(of,1)
                });

            }else{
                cart.orderItemList.forEach(function (orderItem) {
                    app.selected.push(orderItem.itemId)
                });
                this.selected.push(cart.sellerId)
            }
        },

        selectAddItem:function (itemId,cart) {
            var indexOf = this.selected.indexOf(itemId);
            if (indexOf != -1){
                this.selected.splice(indexOf,1);
            } else{
                this.selected.push(itemId)
            }
            var count = 0;
            this.selected.forEach(function (item) {
                cart.orderItemList.forEach(function (orderItem) {
                    if (item==orderItem.itemId){
                        count+= 1;
                    }
                })
            });

            if (cart.orderItemList.length == count){
                this.selected.push(cart.sellerId);
            }else{
                var of = this.selected.indexOf(cart.sellerId);
                if (of != -1){
                    this.selected.splice(of,1);
                }
            }
        }
    },
    computed: {
        selectAll: {
            get: function () {
                var count = 0;
                this.cartList.forEach(function (cart) {
                    count += 1;
                    cart.orderItemList.forEach(function (orderItem) {
                        count += 1;
                    })
                });
                return this.selected.length == count ? true : false;
            },
            set: function (value) {
                var selected = [];
                if (value) {
                    this.cartList.forEach(function (cart) {
                        selected.push(cart.sellerId);
                        cart.orderItemList.forEach(function (orderItem) {
                            selected.push(orderItem.itemId)
                        })
                    });
                }
                this.selected = selected;
            }
        },
    },
    created: function () {
        this.findCartList();
        var href = window.location.href;//获取当前浏览器的url
        if (href.indexOf("getOrderInfo.html") != -1) {
            this.findAddressList();
        }

    }
});