define(["base","app/commonApp","app/systemManage/roleManage/roleManage"],function(base,common,roleManage){
    return {
        main:function(){
            common.init();
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#roleManageAdd"));

                //校验角色名称是否重名
                common.ajax({
                    url: $.base + "/roleManager/checkRole",
                    type: "post",
                    params: {"roleName": params.roleName},
                    success: function (d) {
                        if (d.success) {
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/roleManager/addRole",
                                params:params,
                                type:"post",
                                callback:function(){
                                    /**提交成功后返回父页面并刷新列表**/
                                    base.page.back({
                                        callback:function(){
                                            if(roleManage){
                                                roleManage.refresh();
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
