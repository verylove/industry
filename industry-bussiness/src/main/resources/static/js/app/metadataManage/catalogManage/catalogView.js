define(["base","app/commonApp","app/metadataManage/catalogManage/catalogManage"],function(base,common,catalogManage){

    return {
        main:function(){
            // 初始化赋值
            base.ajax({
                url:$.base+"/catalogManager/queryCatalogById",
                params:{"id":$("#rowId").attr("rid")},
                type:'post',
                success:function(d){
                    base.form.init(d,$("#catalogManage"));
                }
            })
            common.init();
        }
    };
});
