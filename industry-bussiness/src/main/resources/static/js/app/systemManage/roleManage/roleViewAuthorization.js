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
    var zTreeBeforeCheck = function () {
        return false;
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
            dataCallback:setSearchData,
            callback:{
                beforeCheck: zTreeBeforeCheck
            }
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

        }
    };
});