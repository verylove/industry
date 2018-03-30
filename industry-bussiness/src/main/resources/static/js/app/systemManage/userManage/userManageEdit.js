define(["base","app/commonApp","app/systemManage/userManage/userManage"],function(base,common,userManage){
    return {
        main:function(){
            common.init();
            var oldLogin=null;
            base.ajax({
                url:$.base+"/userManager/queryUserById",
                params:{"userId":$("#rowId").attr("rid")},
                type:'post',
                success:function(d){
                    base.form.init(d,$("#userManageEdit"));
                    $("#passwords").val(d.password);
                    $("#roleId").val(d.roleId);
                    $("#comId").val(d.comName);
                    $("#comId").attr("tid",d.comId);
                    oldLogin=d.login;
                }
            })
            // 角色信息
            base.ajax({
                url:$.base+"/roleManager/queryRole",
                type:'post',
                success:function(d){
                    $.each(d,function(i,o){
                        $("#roleId").append('<option value="'+o.roleId+'">'+o.roleName+'</option>')
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
                }
            })

            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/userManager/updateUser",
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
            }

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#userManageEdit"));
                if(!params){return;}
                if(params.password != $("#passwords").val()){
                    base.requestTip({position:"center"}).error("两次密码不统一！");
                    return;
                }
                params.comId = $("#comId").attr("tid");
                params.userId = $("#rowId").attr("rid");

                var login=params.login;
                if(oldLogin==login){
                    setSubmit(params);
                }else{
                    //校验用户名称是否重名
                    common.ajax({
                        url: $.base + "/userManager/checkLoginName",
                        type: "post",
                        params: {"login": params.login},
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