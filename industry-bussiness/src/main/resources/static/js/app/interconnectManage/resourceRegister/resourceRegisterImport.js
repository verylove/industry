define(["base","app/commonApp","app/interconnectManage/resourceRegister/resourceRegister"],function(base,common,resourceRegister){

    return {
        main:function(){
            common.init();
            /*下载模板*/
            $("#download").unbind("click").on("click",function(){
                var catalogueId=$("#catalogueId").val();
                window.location.href = $.base+"/exportController/resciRegisterExport?catalogueId="+catalogueId+"&type=2";
            });
            /*导入*/
            $("#importModal").unbind("click").on("click",function(){
                common.addLoadBg();
                base.form.fileUpload({
                    url: $.base+'/exportController/resciRegisterImport',
                    id:"file",
                    params:{"catalogueId":$("#catalogueId").val()},
                    success:function(d){
                        if(d.code==0){
                            base.requestTip({position:"center"}).error(d.message);
                        }else if(d.code==1){
                            base.requestTip({position:"center"}).success(d.message);
                            resourceRegister.refresh();
                            common.overLoadBg();
                            $('.modal').modal('hide');
                        }
                    },
                    error:function(d){
                        if(d.code==0){
                            base.requestTip({position:"center"}).error(d.message);
                        }else if(d.code==1){
                            base.requestTip({position:"center"}).success(d.message);
                            resourceRegister.refresh();
                            $('.modal').modal('hide');
                        }
                    }
                });
            });
        }
    };
});

