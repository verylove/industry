define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/roleManager/listRole",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:{
                        roleName:$("#roleName").val()
                    }
                };
                return JSON.stringify(d);
            }
        };

        gridOption.columns = [
            { "data": "roleName","sWidth":"33%"},
            { "data": "note","sWidth":"33%"},
            { "data": "roleId","sWidth":"34%"}
        ];
        gridOption.columnDefs = [
            {"render":function(data){
                return '<button class="btn btn-info blue viewAuthorization" rid="'+data+'" title="查看授权"><i class="fa fa-align-justify"></i></button>';
            },
                "targets":2
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
            $("#add").removeAttr("disabled");
            $("#edit,#delete,#view,#authorization").attr("disabled","disabled");
        }
    };
    // var treeObj = null;
    /**设置树组件**/
    // var getTree = function(defaultId,params){
    //     var params = params?params:{};
    //     treeObj = common.tree({
    //         container:$("#treebar"),
    //         // url:$.path+"/api/catalogType/getCatalogTypeTree",
    //         url:"../json/tree.json",
    //         params:params,
    //         defaultId:defaultId?defaultId:null,
    //         callback:{
    //             onClick:function(event,containerId,treeData){
    //                 drawGrid();
    //             }
    //         }
    //     });
    // };

    // var treeUpdate = function(treeId,params){
    //     if(treeObj){
    //         treeObj.destroy();
    //     }
    //     getTree(treeId,params);
    // };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox",
            idDef:'roleId'
        });
        $("#add").removeAttr("disabled");
        $("#edit,#delete,#view,#authorization").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            common.load($.base+"/roleManager/addRoleView ");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/roleManager/editRoleView");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/roleManager/queryRoleView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            checkOccupied(ids);
        });
        /*角色授权*/
        $("#authorization").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/roleManager/authorizationView");
        });
        /*查看授权*/
        $(".viewAuthorization").unbind("click").on("click",function(){
            $("#rowId").attr("rid",$(this).attr("rid"));
            common.load($.base+"/roleManager/roleAuthorizationView");
        });
    };
    /*判断是否被占用*/
    function checkOccupied(ids){
        var str = '';
        $.each(ids,function(i,o){
            str += o +","
        })
        str = str.substring(0,str.length-1);
        base.ajax({
            url:$.base+"/roleManager/checkDeleteRole",
            type:"post",
            params:{"roleIds":str},
            success:function(data){
                //data.success 为false时，表示为不允许删除
                if(data.success){
                    deleGridRows(str);
                }else{
                    base.requestTip({position:"center"}).error(data.message);
                }
            }
        });
    }
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"));
            drawGrid();
        });
    }
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        // treeUpdate($("#treeId").val(),$(this).parent().find("#catalogName").val());//树刷新
        drawGrid();//表格刷新
    };
    /*表格删除操作*/
    function deleGridRows(str){
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除此角色？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/roleManager/deleteRole",
                    type:"post",
                    params:{"roleIds":str},
                    success:function(data){
                        base.requestTip({position:"center"}).success("删除成功！");
                        refresh();
                    }
                });
            }
        });
    }

    //按钮权限
    var setCheckbox=function(){
        $("input[name='cb'],input[name='cball']").change(function(){
            var ids = base.getChecks("cb").val;
            if(ids.length==1){
                $("#add,#edit,#delete,#authorization").attr("disabled","disabled");
                if(ids[0]==1||ids[0]==2||ids[0]==3||ids[0]==4||ids[0]==5||ids[0]==6||ids[0]==7){
                    $("#view").removeAttr("disabled");
                    // base.requestTip({position:"center"}).error("系统默认角色无法修改权限");
                }else{
                    $("#edit,#delete,#view,#authorization").removeAttr("disabled");
                }
            }else if(ids.length>1){
                $("#add,#edit,#delete,#authorization").attr("disabled","disabled");
                if(ids.indexOf("1")>-1||ids.indexOf("2")>-1||ids.indexOf("3")>-1||ids.indexOf("4")>-1||ids.indexOf("5")>-1||ids.indexOf("6")>-1||ids.indexOf("7")>-1){
                    // base.requestTip({position:"center"}).error("系统默认角色无法修改权限");
                }else {
                    $("#delete").removeAttr("disabled");
                }
            }else{
                $("#add").removeAttr("disabled");
                $("#edit,#delete,#view,#authorization").attr("disabled","disabled");
            }
        })
    };

    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            setTreeButton();
            drawGrid();
        },
        refresh:function(){refresh();}
    };
});


