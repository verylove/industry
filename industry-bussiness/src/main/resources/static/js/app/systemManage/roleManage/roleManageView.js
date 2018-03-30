define(["base","app/commonApp","app/systemManage/roleManage/roleManage"],function(base,common,roleManage){
    return {
        main:function(){
            common.init();
            // 初始化赋值
            base.ajax({
                url:$.base+"/roleManager/queryRoleById",
                type:"post",
                params:{"roleId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#roleManageView"))
                }
            })
        }
    };
});
