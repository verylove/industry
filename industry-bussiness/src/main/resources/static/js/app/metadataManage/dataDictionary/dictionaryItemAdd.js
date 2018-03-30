define(["base","app/commonApp","app/metadataManage/dataDictionary/dataDictionary"],function(base,common,dataDictionary){
    return {
        main:function(){
            common.init();
            base.ajax({
                url:$.base+"/dictionarManager/queryDictionarSelect",
                type:"post",
                success:function(d){
                    $.each(d,function(i,o){
                        $("#classId").append("<option value='"+o.id+"'>"+o.name+"</option>");
                    })
                    if($("#treeId").attr("rid")!=0){
                        $("#classId").val($("#treeId").attr("rid"));
                    }
                }
            })

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#dictionaryItem"));
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
                        if (d.success) {
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/dictionarManager/addItem",
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
                        }else{
                            base.requestTip({position:"center"}).error(d.message);
                        }
                    }
                });

            });
        }
    };
});
