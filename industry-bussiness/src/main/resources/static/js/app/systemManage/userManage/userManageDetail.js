define(["base","app/commonApp","app/systemManage/userManage/userManage"],function(base,common,userManage){
    return {
        main:function(){
            base.ajax({
                url:$.base+"/userManager/queryUserById",
                params:{"userId":$("#rowId").attr("rid")},
                type:'post',
                success:function(d){
                    base.form.init(d,$("#userManageDetail"));
                    $("#roleId").val(d.roleId);
                    $("#comId").val(d.comName);
                    $("#comId").attr("tid",d.comId);
                }
            })
            common.init();
        }
    };
});