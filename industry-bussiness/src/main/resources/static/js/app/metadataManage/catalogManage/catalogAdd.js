define(["base","app/commonApp","app/metadataManage/catalogManage/catalogManage"],function(base,common,catalogManage){

    return {
        main:function(){
            common.init();
            //单位信息
            common.ajax({
                url:$.base+"/organizationManager/listOrganizationTree",
                type:"post",
                success:function(d){
                    var treeSelectObj = base.form.treeSelect({
                        container:$("#comId"),
                        data:d,
                        clickCallback:function(event,treeId,treeNode){
                            if(treeNode.level == 0){
                                return "break";
                            }
                            catalogUser(treeNode.id);
                        }
                    });
                    $("#comId").val("请选择");
                }
            })
            //目录审核员
            var catalogUser = function(ids){
                common.ajax({
                    url:$.base+"/userManager/queryRoleUserByComId",
                    type:"post",
                    params:{"roleId":7,"comId":ids},
                    success:function(d){
                        $("#userNmae").empty();
                        $.each(d,function (i,o) {
                            $("#userNmae").append("<option value='"+o.userId+"'>"+o.userName+"</option>");
                        })
                    }
                })
            }

            /**提交操作**/
            common.setSubmitForm($("#requestSubmit"),function() {
                var params = base.form.getParams($("#catalogManage"));
                params.comId = $("#comId").attr("tid");
                params.fieldId = $("#treeId").attr("rid");
                common.submit({
                    url: $.base + "/catalogManager/addCatalog",
                    params: params,
                    type: "post",
                    callback: function () {
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback: function () {
                                if (catalogManage) {
                                    catalogManage.refresh();
                                }
                            }
                        });
                    }
                });
            });
        }
    };
});