$(document).ready(function () {

    $(".one-level").toggle(function () {
        var cate_child = $(this).parents('.cate-type').find('.cate-child');
        if(cate_child.find('.cate-level').length > 0){
            cate_child.slideDown();
        } else{
            var url = cate_child.prev().find('.one-level').attr('href');
            window.location.href = url;
        }
    }, function () {
        var cate_child = $(this).parents('.cate-type').find('.cate-child');
        if(cate_child.find('.cate-level').length > 0){
            cate_child.slideUp();
        }
    });

    $(".two-level").toggle(function () {
        var cate_third = $(this).parents('.cate-level').find('.cate-third');
        if(cate_third.find('.third-wd').length > 0){
            cate_third.slideDown();
        }
        else{
            var url = cate_third.prev().find('.two-level').attr('href');
            window.location.href = url;
        }
    }, function () {
        var cate_third = $(this).parents('.cate-level').find('.cate-third');
        if(cate_third.find('.third-wd').length > 0){
            cate_third.slideUp();
        }
    });

    $('.list-opt li').click(function (){
        $('.list-opt li a').removeClass('active');
        $(this).find('a').addClass('active');
    })

})


function searchForm(){
    $('#search-form').submit();
}

function addShoppingCart(product_id){
    var shopping_cart_token = $.cookie('shopping_cart_token');
    var user_id = $.cookie('user-id');
    var token = $.cookie('token');
    var param = {
        product: {
            productId:product_id,
            quantity:1
        }
    }
    var headers = {
        user_id: user_id,
        token: token,
        shopping_cart_token: shopping_cart_token
    }
    $.ajax({
        type: "post",
        headers: headers,
        url: "/api/shopping_cart/add_product",
        data: JSON.stringify(param),
        contentType:"application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data.status == 0) {
                $.cookie('shopping_cart_token', data.result.shoppingCartToken, {path: '/' });
                findUserCart();
            } else {
               alert(data.message);
            }
        },
        error: function (data) {
            alert('系统繁忙，请稍后再试');
        }
    });
}