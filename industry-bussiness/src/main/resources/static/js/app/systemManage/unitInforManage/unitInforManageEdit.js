define(["base","app/commonApp","app/systemManage/unitInforManage/unitInforManage"],function(base,common,unitInforManage){
    return {
        main:function(){
            common.init();
            //上级单位
            base.ajax({
                url:$.base+"/organizationManager/listOrganizationComboBox",
                type:"post",
                success:function(d){
                    $.each(d,function(i,o){
                        $("#pidName").append('<option value="'+o.id+'">'+o.name+'</option>')
                    })
                }
            })
            // 初始赋值
            var oldComName=null;
            var oldPid=null;
            base.ajax({
                url:$.base+"/organizationManager/queryOrganizationById",
                type:"post",
                params:{"comId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#unitInfor"));
                    $("#pidName").val(d.pid);
                    oldComName=d.comName;
                    oldPid=d.pid;
                }
            })
            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/organizationManager/updateOrganization",
                    params:params,
                    type:"post",
                    callback:function(){
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback:function(){
                                if(unitInforManage){
                                    unitInforManage.refresh();
                                }
                            }
                        });
                    }
                });
            }
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#unitInfor"));
                params.comId = $("#rowId").attr("rid");

                if(oldComName==params.comName&&oldPid==params.pid){
                    setSubmit(params);
                }else{
                    //校验单位名称是否重名
                    var checkObj={
                        "comName":params.comName,
                        "pid":params.pid
                    }
                    common.ajax({
                        url:$.base+"/organizationManager/checkOrganization",
                        type:"post",
                        params:checkObj,
                        success:function(d){
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

