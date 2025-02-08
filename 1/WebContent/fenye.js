//分页
var page_index = 1;//当前页数,默认第一页为首页
var page_total = 0;//总共页数,获total/size获得
var size=16;//每页数量
var total =0;//总共多少条记录
$("#first_page").click(function(){
    if(page_index<=1) return;
    page_index=1;
    $("#currentIndex").html(page_index);
    getDisplayList();
});
$(".prev").click(function(){
    if(page_index<=1) return;
    page_index--;
    $("#currentIndex").html(page_index);
    getDisplayList();
});
$(".next").click(function(){
    if(page_index>=page_total) return;
    page_index++;
    $("#currentIndex").html(page_index);
    getDisplayList();
});
$("#last_page").click(function(){
    if(page_index>=page_total) return;
    page_index = page_total;
    $("#currentIndex").html(page_index);
    getDisplayList();
});
function _confirm(){
    if (!($("#gotoPage").val())) {
        dialogModule.iTips('请输入页数');
        return;
    }
    var current = $("#gotoPage").val();
    current = parseInt(current);
    if(current<1) return;
    if (current>page_total) {
        current = 1;
        $("#gotoPage").val('1');
    };
    page_index=current;
    $("#currentIndex").html(page_index);
    getDisplayList();
}
function num(d){
    if(d>=page_total) return;
    page_index = d;
    getDisplayList();
}

