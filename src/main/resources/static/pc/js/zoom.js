window.onload = function () {
    var booth_small = document.getElementById('booth-small');//小图盒子
    var booth_big = document.getElementById('booth-big');//大图盒子
    var big_pic = document.getElementById('big-pic');//大图图片
    var product_booth = document.getElementById('product-booth');
    var mouse_bg = document.getElementById('mouse-bg');//鼠标白块
    var mul = 5;
    //当某一个模块dispaly:none的时候不能使用offsetWidth获取它的宽高
    booth_small.onmouseover = function () {
        //鼠标进入
        booth_big.style.display = 'block';
        mouse_bg.style.display = 'block';

    }
    booth_small.onmouseout = function () {
        //鼠标离开
        booth_big.style.display = 'none';
        mouse_bg.style.display = 'none';
    }
    booth_small.onmousemove = function (event) {
        var _event = event||window.event;//兼容性处理
        var mouseX = _event.clientX - product_booth.offsetLeft - booth_small.offsetLeft;
        //计算鼠标相对与小图的位置
        var mouseY = _event.clientY - product_booth.offsetTop - booth_small.offsetTop;

        //特殊情况处理，分别靠近四条边的时候
        if(mouseX<mouse_bg.offsetWidth/2){
            mouseX = mouse_bg.offsetWidth/2;
        }
        if(mouseX>booth_small.offsetWidth-mouse_bg.offsetWidth/2){
            mouseX = booth_small.offsetWidth-mouse_bg.offsetWidth/2;
        }
        if(mouseY<mouse_bg.offsetHeight/2){
            mouseY = mouse_bg.offsetHeight/2;
        }
        if(mouseY>booth_small.offsetHeight-mouse_bg.offsetHeight/2){
            mouseY = booth_small.offsetHeight-mouse_bg.offsetHeight/2;
        }
        //计算大图的显示范围
        big_pic.style.left = -mul*mouseX+booth_big.offsetWidth/2+"px";
        big_pic.style.top = -mul*mouseY+booth_big.offsetHeight/2+"px";
        //使鼠标在白块的中间
        mouse_bg.style.left = mouseX-mouse_bg.offsetWidth/2+"px";
        mouse_bg.style.top = mouseY-mouse_bg.offsetHeight/2+"px";

    }
}
