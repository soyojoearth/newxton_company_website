$(function(){
    $(".nav-bar").mouseover(function(){
        $(".nav-bar-bd").addClass('active');
        $(".nav-about").addClass('active');
    }).mouseleave(function (){
        $(".nav-bar-bd").removeClass('active');
        $(".nav-about").removeClass('active');
    });

})

