define(["base","app/commonApp","app/interconnectManage/regionManage/regionManage"],function(base,common,regionManage){
    return {
        main:function(){
            common.init();
            //上级域
            base.ajax({
                url:$.base+"/fieldManager/rootField",
                type:"post",
                success:function(d){
                    $("#pidName").val(d.FIELD_NAME);
                }
            })
            //所属单位
            base.ajax({
                url:$.base+"/organizationManager/listOrganizationTree",
                type:"post",
                success:function(d){
                    var treeSelectObj = base.form.treeSelect({
                        container:$("#comName1"),
                        data:d,
                        clickCallback:function(event,treeId,treeNode){
                            if(treeNode.level == 0){
                                return "break";
                            }
                            regionUser(treeNode.id);
                        }
                    });
                }
            })
            //域管理员
            var regionUser = function(id){
                base.ajax({
                    url:$.base+"/userManager/queryRoleUserByComId",
                    params:{"comId":id,"roleId":2},
                    type:"post",
                    success:function(d){
                        $("#userId").empty();
                        $.each(d,function(i,o){
                            $("#userId").append("<option value='"+o.userId+"'>"+o.userName+"</option>")
                        })
                    }
                })
            }
            var oldFieldName=null;
            // 初始化赋值
            base.ajax({
                url:$.base+"/fieldManager/queryResciFieldById",
                type:"post",
                params:{"resciFieldId":$("#rowId").attr("rid")},
                success:function(d){
                    base.form.init(d,$("#regionManageEdit"))
                    $("#comName1").val(d.comName);
                    $("#comName1").attr("tid",d.comId);
                    regionUser(d.comId);
                    $("#userId").val(d.userId);
                    oldFieldName=d.fieldName;
                }
            })
            /**提交操作**/
            var setSubmit=function(params){
                common.submit({
                    url:$.base+"/fieldManager/updateResciField",
                    params:params,
                    type:"post",
                    callback:function(){
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback:function(){
                                if(regionManage){
                                    regionManage.refresh();
                                }
                            }
                        });
                    }
                });
            }

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#regionManageEdit"));
                params.pid = 1;
                params.comId = Number($("#comName1").attr("tid"));
                params.id = $("#rowId").attr("rid");
                var fieldName=params.fieldName;
                if(oldFieldName==fieldName){
                    setSubmit(params);
                }else{
                    //校验域名称是否重名
                    common.ajax({
                        url: $.base + "/fieldManager/checkFieldName",
                        type: "post",
                        params: {"fieldName": params.fieldName},
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
