function bindEmailCapchaClick() {
    $("#captcha-btn").click(function (event) {
        event.preventDefault();
        var $this = $(this);
        var oldBtnLabel = $this.text()

        var email = $("input[name='email']").val()

        $.ajax({
            url: '/auth/captcha/email?email=' + email,
            method: 'GET',
            success: function (result) {
                console.log(result)

                if (+result['code'] == 200) {
                    var countDown = 6;
                    // 取消按钮的点击事件
                    $this.off("click");

                    var timer = setInterval(function () {
                        $this.text(countDown);
                        countDown -= 1;

                        if (countDown <= 0) {
                            clearInterval(timer);

                            $this.text(oldBtnLabel);

                            //重新绑定点击事件
                            bindEmailCapchaClick();
                        }
                    }, 1000);

                    alert("邮件发送成功!")
                }
                else {
                    alert(result['msg'])
                }
            },
            fail: function (error) {
                console.log(error)
            }
        })
    })
}


$(function () {
    bindEmailCapchaClick();
});