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
        var attr_data = $(this).find('.attr-data');
        var price = attr_data.attr('data-price');
        var discount = attr_data.attr('data-discount');
        var stock = attr_data.attr('data-stock');
        $('.product-promo .sell-price').text('¥' + (price * discount));
        $('.product-promo .orig-price').text('¥' + price);
        $('.product-promo .stock-num').text(stock);
        $(this).addClass('active').siblings().removeClass('active');
    })

    $(".comment-pic li").mouseenter(function () {
        var img_url = $(this).find('img').attr('data-url');
        var booth_large = $(this).parents('.comment-cont').find('.booth-large');
        booth_large.find('img').attr('src', img_url);
        booth_large.show();
    });

    $(".comment-pic").mouseleave(function () {
        $(this).parent().find('.booth-large').hide();
    })

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