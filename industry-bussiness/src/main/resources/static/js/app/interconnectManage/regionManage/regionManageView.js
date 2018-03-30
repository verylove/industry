define(["base","app/commonApp","app/interconnectManage/regionManage/regionManage"],function(base,common,regionManage){
    return {
        main:function(){
            // 初始化赋值
            base.ajax({
                url:$.base+"/fieldManager/queryResciFieldById",
                type:"post",
                params:{"resciFieldId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#regionManageView"))
                }
            })
            common.init();
        }
    };
});

