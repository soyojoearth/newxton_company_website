$(document).ready(function () {
    /* 图片滚动效果 */
    $(".product-thumb").slide({
        titCell: "",
        mainCell: ".thumb-pic",
        autoPage: false,
        effect: "leftLoop",
        autoPlay: false,
        vis: 5
    });

    $(".thumb-pic li").mouseenter(function () {
        var img_url = $(this).find('img').attr('data-url');
        $(this).addClass('active').siblings().removeClass('active');
        $('.small-pic').attr('src', img_url);
        $('.big-pic').attr('src', img_url);
    });

    $(".spec-attr li").click(function () {
        $(this).addClass('active').siblings().removeClass('active');

        var sku_name = '';
        $('.spec-attr li.active').each(function () {
            var attr_name = $(this).find('.attr-name').text();
            sku_name += sku_name == '' ? attr_name : ',' + attr_name;
        })
        $(".sku-info .attr-data").each(function () {
            var this_obj = $(this);
            var data_name = this_obj.attr('data-name');
            var data_price = parseFloat(this_obj.attr('data-price'));
            var data_discount = parseFloat(this_obj.attr('data-discount'));
            var data_stock = this_obj.attr('data-stock');
            if (sku_name == data_name) {
                $('.product-promo .sell-price').text('¥' + (data_price * data_discount).toFixed(2));
                $('.product-promo .orig-price').text('¥' + data_price.toFixed(2));
                $('.product-promo .stock-num').text(data_stock);
            }
        });
    })

    $(".comment-pic li").live("mouseenter", function () {
        var img_url = $(this).find('img').attr('data-url');
        var booth_large = $(this).parents('.comment-cont').find('.booth-large');
        booth_large.find('img').attr('src', img_url);
        booth_large.show();
    });

    $(".comment-pic").live("mouseleave", function () {
        $(this).parent().find('.booth-large').hide();
    });

    $('.reduce-num').click(function () {
        var num_obj = $('.num-value');
        var min_num = num_obj.attr('min-num');
        var current_num = num_obj.val();
        current_num--;
        if (current_num < min_num) {
            alert('不能小于最小购买数')
            return false;
        }
        num_obj.val(current_num);
    })

    $('.add-num').click(function () {
        var num_obj = $('.num-value');
        var max_num = num_obj.attr('max-num');
        var current_num = num_obj.val();
        current_num++;
        if (current_num > max_num) {
            alert('不能大于最大购买数')
            return false;
        }
        num_obj.val(current_num);
    })
});


function addShoppingCart(product_id) {
    var sku_array = new Array();
    $('.spec-attr li.active').each(function () {
        var this_obj = $(this);
        var sku_value_name = this_obj.find('.attr-name').text();
        var sku_key_name = this_obj.parents('.spec-info').find('.spec-title').text();
        var sku = {skuKeyName: sku_key_name, skuValueName: sku_value_name};
        sku_array.push(sku);
    })
    if(sku_array.length != $('.spec-info').length){
        alert('请选择商品信息')
        return false;
    }
    var num = $('.num-value').val();
    var stock_num = parseInt($('.stock-num').text());
    if(num > stock_num){
        alert('所选商品库存不足，请选择其它商品')
        return false;
    }

    var shopping_cart_token = $.cookie('shopping_cart_token');
    var user_id = $.cookie('user-id');
    var token = $.cookie('token');
    var param = {
        product: {
            productId: product_id,
            quantity: num,
            sku: sku_array
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
        contentType: "application/json;charset=utf-8",
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