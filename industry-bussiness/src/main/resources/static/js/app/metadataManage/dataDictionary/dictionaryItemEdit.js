define(["base","app/commonApp","app/metadataManage/dataDictionary/dataDictionary"],function(base,common,dataDictionary){
    return {
        main:function(){
            common.init();
            var oldClassId=null;
            var oldItemName=null;
            // 初始化赋值
            base.ajax({
                url:$.base+"/dictionarManager/queryItemById",
                type:"post",
                params:{"id":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#dictionaryItem"))
                    $("#dictionarId").val(d.dictionarName);
                    $("#dictionarId").attr("rid",d.dictionarId);
                    oldClassId=d.dictionarId;
                    oldItemName=d.itemName;
                }
            })
            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/dictionarManager/updateItem",
                    params:params,
                    type:"post",
                    callback:function(){
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback:function(){
                                if(dataDictionary){
                                    dataDictionary.refresh();
                                }
                            }
                        });
                    }
                });
            }

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#dictionaryItem"));
                var ids = base.getChecks("cb").val;
                params.id = ids[0];
                params.classId = $("#dictionarId").attr("rid");
                if(oldClassId==params.classId&&oldItemName==params.itemName){
                    setSubmit(params);
                }else{
                    //校验字典项类别名称和字典项内容
                    var checkObj={
                        "classId":params.classId,
                        "itemName":params.itemName
                    }
                    common.ajax({
                        url: $.base + "/dictionarManager/addItemCheck",
                        type: "post",
                        params: checkObj,
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

