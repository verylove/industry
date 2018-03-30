define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/dictionarManager/listDictionar",
            "contentType":"application/json",
            type:"post",
            data:function(d){
                var param = base.form.getParams($("#gridForm"))
                param.classId = $("#treeId").attr("rid");
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
            { "data": "itemName","sWidth":"30%"},
            { "data": "itemAlias","sWidth":"30%"},
            { "data": "itemContent","sWidth":"32%"}
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
            $("#addItem").removeAttr("disabled");
            $("#editItem,#deleteItem").attr("disabled","disabled");
        }
    };
    var treeObj = null;
    var setSearchData=function(data){
        var dataArr=data.data;
        dataArr.unshift({name: "全部", pid: null, id: 0});
        return data;
    }
    /**设置树组件**/
    var getTree = function(defaultId){
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/dictionarManager/queryDictionarTree",
            defaultId:defaultId?defaultId:null,
            dataCallback:setSearchData,
            callback:{
                onClick:function(event,containerId,treeData){
                    common.reset($("#gridForm"));
                    $("#treeId").attr("rid",treeData.id);
                    drawGrid();
                    if(treeData.id == 0){
                        $("#editType,#deleteType,#addItem").attr("disabled","disabled");
                    }else{
                        $("#editType,#deleteType,#addItem").removeAttr("disabled");
                    }
                }
            }
        });
    };

    var treeUpdate = function(treeId){
        if(treeObj){
            treeObj.destroy();
        }
        getTree(treeId);
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox"
        });
        $("#addItem").removeAttr("disabled");
        $("#editItem,#deleteItem").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增字典类型*/
        $("#addType").unbind("click").on("click",function(){
            common.load($.base+"/dictionarManager/dictionaryTypeAdd");
        })
        /*编辑字典类型*/
        $("#editType").unbind("click").on("click",function(){
            base.ajax({
                url:$.base+"/dictionarManager/checkDelDictionar",
                type:"post",
                params:{"ids":$("#treeId").attr("rid")},
                success:function(data){
                    //data.success 为false时，表示为不允许修改
                    if(data.success){
                        common.load($.base+"/dictionarManager/dictionaryTypeEdit");
                    }else{
                        base.requestTip({position:"center"}).error(data.message);
                    }
                }
            });
        })
        /*删除字典类型*/
        $("#deleteType").unbind("click").on("click",function(){
            base.ajax({
                url:$.base+"/dictionarManager/checkDelDictionar",
                type:"post",
                params:{"ids":$("#treeId").attr("rid")},
                success:function(data){
                    //data.success 为false时，表示为不允许删除
                    if(data.success){
                        base.confirm({
                            label:"提示",
                            text:"<div style='text-align:center;font-size:13px;'>确定删除此字典类型吗？</div>",
                            confirmCallback:function(){
                                common.ajax({
                                    url:$.base+"/dictionarManager/deleteDictionar",
                                    type:"post",
                                    params:{"ids":$("#treeId").attr("rid")},
                                    success:function(data){
                                        base.requestTip({position:"center"}).success("删除成功！");
                                        treeUpdate("0");//树刷新
                                        drawGrid();//表格刷新
                                    }
                                });
                            }
                        });
                    }else{
                        base.requestTip({position:"center"}).error(data.message);
                    }
                }
            });
        });
        /*新增字典项*/
        $("#addItem").unbind("click").on("click",function(){
            common.load($.base+"/dictionarManager/dictionaryItemAdd");
        })
        /*编辑字典项*/
        $("#editItem").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            base.ajax({
                url:$.base+"/dictionarManager/itemDeleteCheck",
                type:"post",
                params:{"ids":ids[0]},
                success:function(data) {
                    //data.success 为false时，表示为不允许修改
                    if (data.success) {
                        common.load($.base+"/dictionarManager/dictionaryItemEdit");
                    }else{
                        base.requestTip({position:"center"}).error(data.message);
                    }
                }
            });
        })
        /*批量删除字典类型项*/
        $("#deleteItem").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            var str = '';
            $.each(ids,function (i,o) {
                str += o +",";
            })
            str = str.substring(0,str.length-1);
            base.ajax({
                url:$.base+"/dictionarManager/itemDeleteCheck",
                type:"post",
                params:{"ids":str},
                success:function(data) {
                    //data.success 为false时，表示为不允许删除
                    if (data.success) {
                        deleGridRows(str);
                    }else{
                        base.requestTip({position:"center"}).error(data.message);
                    }
                }
            });
        });
    };
    /*搜索重置按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        // 重置按钮
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"))
            drawGrid();
        });
    }
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        treeUpdate($("#treeId").attr("rid"));//树刷新
        drawGrid();
    };

    /*表格删除操作*/
    function deleGridRows(str){
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/dictionarManager/deleteItem",
                    type:"post",
                    params:{"ids":str},
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
                $("#addItem").attr("disabled","disabled");
                $("#editItem,#deleteItem").removeAttr("disabled");
            }else if(ids.length>1){
                $("#addItem,#editItem").attr("disabled","disabled");
                $("#deleteItem").removeAttr("disabled");
            }else{
                $("#addItem").removeAttr("disabled");
                $("#editItem,#deleteItem").attr("disabled","disabled");
            }
        })
    };

    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            getTree("1");
            setTreeButton();
            // 滚动条
            base.scroll({
                container:$("#treeAside"),
                scrollOption:{
                    mouseWheel: !0,
                    scrollInertia: 500,
                    callbacks: {},
                    axis:"yx"
                }
            })
        },
        refresh:function(){refresh();}
    };
});


