function submitForm(){
    var guestCompany = $('input[name=guestCompany]').val();
    var guestName = $('input[name=guestName]').val();
    var guestPhone = $('input[name=guestPhone]').val();
    var guestEmail = $('input[name=guestEmail]').val();
    var messageContent = $('textarea[name=messageContent]').val();
    if(!messageContent){
        alert('至少填上内容');
        return false;
    }
    var param = {
        guestCompany: guestCompany,
        guestName: guestName,
        guestPhone: guestPhone,
        guestEmail: guestEmail,
        messageContent: messageContent
    }
    $.ajax({
        type: "post",
        url: "/api/guestmessage/create",
        data: JSON.stringify(param),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data.status == 0) {
                alert('留言成功')
                window.location.reload();
            } else {
                alert(data.message);
            }
        },
        error: function (data) {
            alert('系统繁忙，请稍后再试');
        }
    });
}