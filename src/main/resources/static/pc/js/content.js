
var current = 0;//圆点下标
var timer; //定时器
$(function() {
    doSlider();
    start();
    dotClick();
});
//定时器 播放
function start() {
    $('.slider-banner').hover(function(){
        clearInterval(timer);
    },function(){
        timer=setInterval(slider,3000);
    });

    timer=setInterval(slider,3000);
}

//图片轮播
function doSlider() {
    if (current == $('.slider-banner-dot a').length) { //最后一张
        current = 0; //第一张
    }
    $('.slider-banner-pic a').removeClass('banner-active').eq(current).addClass('banner-active');
    $('.slider-banner-dot a').removeClass('dot-active').eq(current).addClass('dot-active');
}

//圆点点击事件
function dotClick(){
    // 点击圆点切换图片
    $('.slider-banner-dot a').click(function(){
        current=$(this).index();
        doSlider();
    });
}

//定时事件
function slider(){
    current++;
    doSlider();
}
