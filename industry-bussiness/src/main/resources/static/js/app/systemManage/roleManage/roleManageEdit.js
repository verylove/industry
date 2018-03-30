define(["base","app/commonApp","app/systemManage/roleManage/roleManage"],function(base,common,roleManage){
    return {
        main:function(){
            common.init();
            var oldRoleName=null;
            // 初始化赋值
            base.ajax({
                url:$.base+"/roleManager/queryRoleById",
                type:"post",
                params:{"roleId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#roleManageEdit"));
                    oldRoleName=d.roleName;
                }
            })
            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/roleManager/updateRole",
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
            }
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#roleManageEdit"));
                params.roleId = $("#rowId").attr("rid");

                var roleName=params.roleName;
                if(oldRoleName==roleName){
                    setSubmit(params);
                }else{
                    //校验角色名称是否重名
                    common.ajax({
                        url: $.base + "/roleManager/checkRole",
                        type: "post",
                        params: {"roleName": params.roleName},
                        success: function (d) {
                            if(d.success){
                                setSubmit(params);
                            }else{
                                base.requestTip({position:"center"}).error(d.message);
                            }
                        }
                    });
                }
            });
        }
    };
});
