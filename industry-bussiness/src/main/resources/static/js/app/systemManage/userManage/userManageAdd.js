define(["base","app/commonApp","app/systemManage/userManage/userManage"],function(base,common,userManage){
    return {
        main:function(){
            // 角色信息
            base.ajax({
                url:$.base+"/roleManager/queryRole",
                type:'post',
                success:function(d){
                    $.each(d,function(i,o){
                        $("#userManageAdd #roleId").append('<option value="'+o.roleId+'">'+o.roleName+'</option>')
                    })
                }
            })
            //单位信息
            common.ajax({
                url:$.base+"/organizationManager/listOrganizationTree",
                type:"post",
                success:function(d){
                    var treeSelectObj = base.form.treeSelect({
                        container:$("#comId"),
                        data:d,
                        clickCallback:function(event,treeId,treeNode){
                            if(treeNode.level == 0){
                                return "break";
                            }
                        }
                    });
                    //$("#comId").val("请选择");
                }
            })
            common.init();
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#userManageAdd"));
                if(!params){return;}
                if(params.password != $("#passwords").val()){
                    base.requestTip({position:"center"}).error("两次密码不统一！");
                    return;
                }
                params.comId = $("#comId").attr("tid");
                //校验用户名称是否重名
                common.ajax({
                    url: $.base + "/userManager/checkLoginName",
                    type: "post",
                    params: {"login": params.login},
                    success: function (d) {
                        if (d.success) {
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/userManager/addUser",
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