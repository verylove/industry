define(["base","app/commonApp","app/metadataManage/dataDictionary/dataDictionary"],function(base,common,dataDictionary){
    return {
        main:function(){
            common.init();
            // 初始化赋值
            var oldDictionarName=null;
            base.ajax({
                url:$.base+"/dictionarManager/queryDictionarById",
                type:"post",
                params:{"id":$("#treeId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#dictionaryType"))
                    if(d.dictionarType=="0"){
                        $("#dictionarType").val("系统字典");
                    }else if(d.dictionarType=="1"){
                        $("#dictionarType").val("用户字典");
                    }
                    oldDictionarName=d.dictionarName;
                }
            })
            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/dictionarManager/updateDictionar",
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
                var params = base.form.getParams($("#dictionaryType"));
                params.id = $("#treeId").attr("rid");
                var dictionarName=params.dictionarName;
                if(oldDictionarName==dictionarName){
                    setSubmit(params);
                }else{
                    //校验字典是否重名
                    common.ajax({
                        url: $.base + "/dictionarManager/addDictionarCheck",
                        type: "post",
                        params: {"dictionarName": dictionarName},
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