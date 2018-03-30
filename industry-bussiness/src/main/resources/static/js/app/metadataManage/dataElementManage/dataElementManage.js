define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/dataManager/listData",
            contentType:"application/json",
            type:"post",
            data:function(d){
                var param = base.form.getParams($("#gridForm"));
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
            { "data": "dataName","sWidth":"11%"},
            { "data": "dataEname","sWidth":"11%"},
            { "data": "versionName","sWidth":"12%"},
            { "data": "itemName","sWidth":"12%"},
            { "data": "dataType","sWidth":"12%"},
            { "data": "dataLength","sWidth":"11%"},
            { "data": "dictionarName","sWidth":"11%"},
            { "data": "enabledState","sWidth":"12%"}
        ];
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                return row.row+1;
            },
                "targets":0
            },
            {"render":function(data,type,rowData,row){
                var but = '';
                var color = "";
                if(data == "0"){
                    but = "正常";
                    color="blue";
                }else {
                    but = "停用";
                    color="red";
                }
                return "<button class='btn btn-info "+color+" status' rid='"+rowData.id+"' title='"+but+"'><span>"+but+"</span></button>";
            },
                "targets":8
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
            common.load($.base+"/dataManager/dataElementAdd");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            base.ajax({
                url:$.base+"/dataManager/deleteDataCheck",
                type:"post",
                params:{"ids":ids[0]},
                success:function(data){
                    //data.success 为false时，表示为不允许修改
                    if(data.success){
                        common.load($.base+"/dataManager/dataElementEdit");
                    }else{
                        base.requestTip({position:"center"}).error(data.message);
                    }
                }
            });
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/dataManager/dataElementView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            checkOccupied(ids);
        });

        $(".status").unbind("click").on("click",function(){
            var txt=""; var status="";
            if($(this).attr("title")=='正常'){
                txt="停用";
                status="1";
            }else{
                txt="启用";
                status="0";
            }
            var params={
                "id":$(this).attr("rid"),
                "enabledState":status,
            };
            base.confirm({
                label:"提示",
                text:"<div style='text-align:center;font-size:13px;'>确定"+txt+"此数据元？</div>",
                confirmCallback:function(){
                    common.ajax({
                        url:$.base+"/dataManager/stateChange",
                        type:'post',
                        params:params,
                        success:function(d){
                            if(d.success){
                                base.requestTip({position:"center"}).success(d.message);
                                refresh();
                            }else{
                                base.requestTip({position:"center"}).error(d.message);
                            }
                        }
                    })
                }
            });
        });

        /*导出*/
        $("#export").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            var params = base.form.getParams($("#gridForm"));
            window.location.href = encodeURI(encodeURI($.base+"/exportController/dataElementExport?ids="+ids+"&type=1"+"&dataName="+(params.dataName?params.dataName:'')+"&itemId="+params.itemId+"&versionId="+params.versionId));
        });

        /*导入*/
        $("#import").unbind("click").on("click",function(){
            var modal = base.modal({
                width:700,
                height:270,
                label:"数据元导入",
                modalOption:{"backdrop":"static","keyboard":false},
                url:$.base+"/exportController/dataElementImportPage",
                buttons:[
                    {
                        label:"确定",
                        cls:"btn btn-info",
                        clickEvent:function(){
                            modal.hide();
                        }
                    }
                ],
                callback:function(){
                }
            });
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
            url:$.base+"/dataManager/deleteDataCheck",
            type:"post",
            params:{"ids":str},
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

    /*搜索重置按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid()
        });
        // 重置按钮
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"))
            drawGrid();
        });
    }

    //按钮权限
    var setCheckbox=function(){
        $("input[name='cb'],input[name='cball']").change(function(){
            var ids = base.getChecks("cb").val;
            if(ids.length==1){
                $("#add").attr("disabled","disabled");
                $("#edit,#delete,#view").removeAttr("disabled");
            }else if(ids.length>1){
                $("#add,#edit,#view").attr("disabled","disabled");
                $("#delete").removeAttr("disabled");
            }else{
                $("#add").removeAttr("disabled");
                $("#edit,#delete,#view").attr("disabled","disabled");
            }
        })
    };

    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        drawGrid();//表格刷新
    };
    /*表格删除操作*/
    function deleGridRows(str){
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/dataManager/deleteData",
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
    var initi = function () {
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:"post",
            params:{"classId":"2"},
            success:function (d) {
                $.each(d,function(i,o){
                    $("#gridForm select[name='itemId']").append("<option value='"+o.id+"'>"+o.itemName+"</option>")
                })
            }
        })
        // 版本号
        // 版本号
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:'post',
            params:{"classId":"3"},
            success:function(d){
                $.each(d,function(i,o){
                    $("#gridForm select[name='versionId']").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                })
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
            initi();
        },
        refresh:function(){refresh();}
    };
});


