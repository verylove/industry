define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        // debugger;
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/fieldManager/listResciField",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"))
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };

        gridOption.columns = [
            { "data": "id","sWidth":"8%"},
            { "data": "fieldName","sWidth":"18%"},
            { "data": "userName","sWidth":"18%"},
            { "data": "pidName","sWidth":"18%"},
            { "data": "comName","sWidth":"18%"},
            { "data": "telephone","sWidth":"20%"}
        ];
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                return row.row+1;
            },
                "targets":0
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
            $("#add").removeAttr("disabled");
            $("#edit,#delete,#view").attr("disabled","disabled");
        }
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox"
        });
        $("#add").removeAttr("disabled");
        $("#edit,#delete,#view").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            common.load($.base+"/fieldManager/regionManageAdd");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/fieldManager/regionManageEdit");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/fieldManager/regionManageView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            checkOccupied(ids);
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
            url:$.base+"/fieldManager/checkDelField",
            type:"post",
            params:{"resciFieldIds":str},
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
    /*树搜索、重置按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            refresh();

        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"))
            refresh();
        });
    }
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        drawGrid();//表格刷新
    };
    /*表格删除操作*/
    function deleGridRows(str){
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除此吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/fieldManager/delResciField",
                    type:"post",
                    params:{"resciFieldIds":str},
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
                $("#add").attr("disabled","disabled");
                if(ids[0]=="1"){
                    $("#view").removeAttr("disabled");
                }else{
                    $("#edit,#delete,#view").removeAttr("disabled");
                }
            }else if(ids.length>1){
                $("#add,#edit,#view").attr("disabled","disabled");
                if(ids.indexOf("1")>-1){
                    $("#delete").attr("disabled","disabled");
                }else{
                    $("#delete").removeAttr("disabled");
                }

            }else{
                $("#add").removeAttr("disabled");
                $("#edit,#delete,#view").attr("disabled","disabled");
            }
        })
    };
    var loading = function () {
        //目录审核单位
        common.ajax({
            url:$.base+"/organizationManager/listOrganizationTree",
            type:"post",
            success:function(d){
                var treeSelectObj = base.form.treeSelect({
                    container:$("#comName"),
                    data:d,
                    clickCallback:function(event,treeId,treeNode){
                        if(treeNode.level == 0){
                            return "break";
                        }
                    }
                });
            }
        })
    }
    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            setTreeButton();
            drawGrid();
            loading();
        },
        refresh:function(){refresh();}
    };
});


