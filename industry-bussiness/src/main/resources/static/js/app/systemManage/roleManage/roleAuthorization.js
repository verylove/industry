define(["base","app/commonApp","app/systemManage/roleManage/roleManage"],function(base,common,roleManage){

    var treeObj = null;
    var setSearchData=function(data){
        $.each(data,function(i,v){
            if(v.selected){
                v.font={'color':'#039ae6','font-weight':'bold!important'}
            }
        });
        return data;
    }

    /**设置树组件**/
    var getTree = function(){
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/roleManager/queryMenu",
            type:"post",
            params:{"roleId":$("#rowId").attr("rid")},
            checked:true,
            expandAll:true,
            dataCallback:setSearchData
        });
    };

    /*树搜索按钮*/
    // var setTreeButton=function(){
    //     $("#search").unbind().on("click",function(){
    //         var resName = $("#menuName").val();
    //         //取消所有之前选中的节点
    //         treeObj.cancelSelectedNode();
    //         //将节点全部收起来
    //         treeObj.expandAll(false);
    //         //被模糊匹配选中的节点
    //         var timeOut = setTimeout(function(){
    //             var nodes = treeObj.getNodesByParamFuzzy("name", resName, null);
    //             for(var i=0;i<nodes.length;i++){
    //                 var parent = nodes[i].getParentNode();
    //                 if(!parent.open){
    //                     treeObj.expandNode(parent,true,true);
    //                 }
    //                 treeObj.updateNode(nodes[i]);
    //                 treeObj.selectNode(nodes[i],true);
    //             }
    //         },400)
    //     });
    // }

    return {
        main:function(){
            common.init();
            getTree();
            // setTreeButton();

            base.scroll({
                container:"#treebar"
            });
            common.setSubmitForm($("#requestSubmit"),function(){
                var nodes=treeObj.getCheckedNodes(true);
                var str = ''
                $.each(nodes,function(i,v){
                    str += v.id +','
                });
                str = str.substring(0,str.length-1);
                var params={
                    "menuId":str,
                    "roleId": $("#rowId").attr("rid")
                }
                /**提交操作**/
                common.submit({
                    url:$.base+"/roleManager/authorization",
                    params:params,
                    type:"post",
                    callback:function(){
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback:function(){
                                if(roleManage){
                                    roleManage.refresh();
                                }
                            }
                        });
                    }
                })
            });
        }
    };
});