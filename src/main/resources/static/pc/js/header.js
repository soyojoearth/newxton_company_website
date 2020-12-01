$(function(){
    $(".nav-bar").mouseenter(function(){
        $(".nav-bar-bd").addClass('active');
        $(".nav-about").addClass('active');
    }).mouseleave(function (){
        $(".nav-bar-bd").removeClass('active');
        $(".nav-about").removeClass('active');
    });

    $('.nav-user-buy').mouseenter(function (){
        $(".nav-cart-list").addClass('active-cart');
    }).mouseleave(function (){
        $(".nav-cart-list").removeClass('active-cart');
    });

    findUserCart();

})

//查询用户购物车信息
function findUserCart(){
    var product_id = $.cookie('guestToken');
    var param = {
        guestToken: product_id
    }
    $.ajax({
        type: "post",
        /*headers: {user_id: 0,token: null},*/
        url: "/api/shopping_cart/detail",
        data: JSON.stringify(param),
        contentType:"application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data.status == 0) {
                var cart_data = data.result.itemList;
                var cart_html = '';
                var cart_num = 0;
                if(cart_data.length > 0){
                    var data_value = {data: cart_data};
                    cart_html = $("#cartTemp").render(data_value);
                    cart_num = cart_data.length;
                }
                $('.nav-cart-num').text(cart_num);
                $(".nav-cart-list").html(cart_html);

            }
        }
    });
}
