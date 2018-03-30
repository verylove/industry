define(["base","app/commonApp","app/metadataManage/dataElementManage/dataElementManage"],function(base,common,dataElementManage){

    return {
        main:function(){
            common.init();
            /*下载模板*/
            $("#download").unbind("click").on("click",function(){
                window.location.href = $.base+"/exportController/dataElementExport?type=2";
            });
            /*导入*/
            $("#importModal").unbind("click").on("click",function(){
                base.form.fileUpload({
                    url: $.base+'/exportController/dataElementImport',
                    id:"file",
                    params:{"fieldId":$("#treeId").attr("rid")},
                    success:function(d){
                        if(d.code==0){
                            base.requestTip({position:"center"}).error(d.message);
                        }else if(d.code==1){
                            base.requestTip({position:"center"}).success(d.message);
                            dataElementManage.refresh();
                            $('.modal').modal('hide');
                        }
                    },
                    error:function(d){
                        if(d.code==0){
                            base.requestTip({position:"center"}).error(d.message);
                        }else if(d.code==1){
                            base.requestTip({position:"center"}).success(d.message);
                            dataElementManage.refresh();
                            $('.modal').modal('hide');
                        }
                    }
                });
            });
        }
    };
});
