$(document).ready(function () {

    $(".one-level").toggle(function () {
        var cate_child = $(this).parents('.cate-type').find('.cate-child');
        if(cate_child.find('.cate-level').length > 0){
            cate_child.slideDown();
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