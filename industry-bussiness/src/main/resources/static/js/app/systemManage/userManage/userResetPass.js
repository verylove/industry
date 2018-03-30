define(["base","app/commonApp","app/systemManage/userManage/userManage"],function(base,common,userManage){
    return {
        main:function(){
            common.init();
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = {}
                params.userId = $("#rowId").attr("rid");
                params.password = $("#password").val();
                if(!params){return;}
                if(params.password != $("#passwords").val()){
                    base.requestTip({position:"center"}).error("两次密码不统一！");
                    return;
                }
                //校验旧密码是否正确
                common.ajax({
                    url: $.base + "/userManager/checkOldPassword",
                    type: "post",
                    params: {"userId": params.userId,"password":$("#oldPassword").val()},
                    success: function (d) {
                        if (d.success) {
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/userManager/resetPassword",
                                params:params,
                                type:"post",
                                callback:function(){
                                    /**提交成功后返回父页面并刷新列表**/
                                    base.page.back({
                                        callback:function(){
                                            if(userManage){
                                                userManage.refresh();
                                            }
                                        }
                                    });
                                }
                            });
                        }else{
                            base.requestTip({position:"center"}).error(d.message);
                        }
                    }
                });

            });
        }
    };
});