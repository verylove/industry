define(["base","app/commonApp","app/metadataManage/dataDictionary/dataDictionary"],function(base,common,dataDictionary){
    return {
        main:function(){
            common.init();
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#dictionaryType"));
                params.parentId = 0;
                //校验字典是否重名
                common.ajax({
                    url:$.base+"/dictionarManager/addDictionarCheck",
                    type:"post",
                    params:{"dictionarName":params.dictionarName},
                    success:function(d){
                        if(d.success){
                            /**提交操作**/
                            common.submit({
                                url:$.base+"/dictionarManager/addDictionar",
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
