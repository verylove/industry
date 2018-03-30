define(["base","app/commonApp","app/systemManage/unitInforManage/unitInforManage"],function(base,common,unitInforManage){
    return {
        main:function(){
            // 初始赋值
            base.ajax({
                url:$.base+"/organizationManager/queryOrganizationById",
                type:"post",
                params:{"comId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#unitInfor"))
                }
            })
            common.init();
        }
    };
});

