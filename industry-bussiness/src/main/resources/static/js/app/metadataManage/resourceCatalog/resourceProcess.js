define(["base", "app/commonApp", "app/metadataManage/resourceCatalog/resourceCatalog"], function (base, common, resourceCatalog) {
    var loading = function(){
        base.ajax({
            url:$.base+"/catalogingManager/viewReleaseProcess",
            type:"post",
            params:{"id":$("#rowId").attr("rid")},
            success:function (d) {
                base.form.init(d.catalogUEMap,$("#table1"));
                if(d.dataExamineMap){
                    if(d.dataExamineMap.examineState == 1){
                        d.dataExamineMap.examineState = "同意发布";
                    }else if(d.dataExamineMap.examineState == 0) {
                        d.dataExamineMap.examineState = "拒绝发布";
                    }
                }
                base.form.init(d.dataExamineMap,$("#table2"));
                if (d.dataExamineMap) {
                    $("#steps .updateTime").html(d.dataExamineMap.updateTime)
                }
                $("#steps .submitTime").html(d.catalogUEMap.submitTime)
                $("#steps .releaseTime").html(d.catalogUEMap.releaseTime)
                $("#steps .revokeTime").html(d.catalogUEMap.revokeTime)
            }
        })
    }
    return {
        main: function () {
            common.init();
            loading();
        }
    };
});