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
                        $("#comName").append('<option value="'+o.id+'">'+o.name+'</option>')
                    })
                }
            })

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#unitInfor"));

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
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/organizationManager/addOrganization",
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
                        }else{
                            base.requestTip({position:"center"}).error(d.message);
                        }
                    }
                });
            });
        }
    };
});

