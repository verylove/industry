define(["base","app/commonApp","cookies"],function(base,common){
    var setCentent = function(){
        //情况用户名密码
        $("input").val('');
        //验证码的赋值，与事件绑定
        $(".captcha_img").on("click",function  () {
            $(this).attr("src",$.base+"/index/code?"+Math.random())
        }).click()
        //回车键的时间绑定
        document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==13){
                $("#login_button").click();
            }
        }
        //登录按钮事件的绑定
        $("#login_button").click(function(){
            var param = {
                "login": $("#loginName").val(),
                "password": $("#passWord").val(),
                "code":$("#code").val(),
            }
            $(".ui-form-error").remove();
            if(param.login == ""){
                $("#loginTitle").parent().append('<div class="ui-form-error">用户名不能为空！</div>');
                return;
            }else if(param.password == ""){
                $("#passTitle").parent().append('<div class="ui-form-error">密码不能为空！</div>');
                return;
            }else if(param.code == ""){
                $("#codeTitle").parent().append('<div class="ui-form-error">验证码不能为空！</div>');
                return;
            }
            $.support.cors = true;
            $.ajax({
                url:$.base+"/index/login",
                data:JSON.stringify(param),
                type:"post",
                contentType:"application/json",
                xhrFields:{withCredentials:true},
                success:function(d){
                    if(d.success){
                        window.location.href = $.base+"/index/main"
                    }else {
                        $("#codeTitle").parent().append('<div class="ui-form-error">'+d.message+'</div>');
                    }
                }
            })
        })
    }
    //登录页的样式设置
    var setStyle =function  () {
        var mainH = $('#mainBody').height();
        var mainW = $('#mainBody').width();
        var photoImgH = parseFloat(mainH)-80;
        //左侧图形的样式
        $("#photo img").height(photoImgH);
        $('#photo img').css({'max-height':mainH})
        $('#photo img').css({top:(mainH-photoImgH)/2,left:mainW/2-$("#photo img").width()*3/3.5});

        var headHeight = $("#head").height();
        var logoHeight = $("#logo").height();
        var Top = (headHeight-logoHeight)/2;
        var Bottom = (headHeight - 19)/3;
        if(mainH < 600){
            $("#logo").css({top :Top+ "px"});
            $("#main").css({"left":"50%"});
        }else{
            $("#main").css({"left":"55%"});
            $("#logo").css({top :Top+17+ "px"});
        }
        //版权信息的样式
        $(".foot_version_text").css({"margin-bottom" :Bottom+ "px","color":"#637aaa"});
    }
    return {
        main:function(){
            setStyle();
            setCentent();
        }
    };
});