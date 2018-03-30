define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var level = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/catalogingManager/catalogingList",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"))
                var catalogId = $("#treeId").val();
                param.catalogId = catalogId == 1?"":catalogId;
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
            { "data": "resciName","sWidth":"13%"},
            { "data": "resciCode","sWidth":"13%"},
            { "data": "resciLevel","sWidth":"13%"},
            { "data": "topicName","sWidth":"13%"},
            { "data": "industryName","sWidth":"14%"},
            { "data": "oid","sWidth":"13%"},
            { "data": "resciState","sWidth":"13%"}
        ];
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                return row.row+1 +"<input type='hidden' class='resciState' value='"+rowData.resciState+"'>";
            },
                "targets":0
            },
            {"render":function(data,type,rowData,row){
                var html = "待提交";
                switch (data){
                    case -1:
                        html = "未完成";
                        break;
                    case 0:
                        html = "待提交";
                        break;
                    case 1:
                        html = "待审核";
                        break;
                    case 2:
                        html = "审核中";
                        break;
                    case 3:
                        html = "待发布";
                        break;
                    case 4:
                        html = "已发布";
                        break;
                    case 5:
                        html = "已撤销";
                        break;
                    case 6:
                        html = "已拒绝";
                        break;
                }
                return html;
            },
                "targets":7
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
            var treeLevel=$("#treeId").attr("rid");
            $("#modify,#view,#delete,#submit,#flowPath,#publish,#cancel").attr("disabled","disabled");
            if(treeLevel=="0"){
                $("#add").attr("disabled","disabled");
            }else if(treeLevel=="2"){
                $("#add").removeAttr("disabled");
            }
        }
    };
    var treeObj = null;
    /**设置树组件**/
    var getTree = function(defaultId,params){
        var params = params?params:{};
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/catalogingManager/catalogingTree",
            params:params,
            defaultId:defaultId?defaultId:null,
            callback:{
                beforeClick:function (event,containerId,treeData) {
                  if(containerId.level == 1){
                      return false;
                  }
                },
                onClick:function(event,containerId,treeData){
                    if(treeData.level==0 || treeData.level==2){
                        $("#treeId").val(treeData.id).attr("rid",treeData.level);
                        drawGrid();
                        if(treeData.level==0){
                            $("#add").attr("disabled","disabled");
                            level = false;
                        }else {
                            level = true;
                            $("#add").removeAttr("disabled");
                        }
                    }
                }
            }
        });
    };

    var treeUpdate = function(treeId,params){
        if(treeObj){
            treeObj.destroy();
        }
        getTree(treeId,params);
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox"
        });
        var treeLevel=$("#treeId").attr("rid");
        $("#modify,#view,#delete,#submit,#flowPath,#publish,#cancel").attr("disabled","disabled");
        if(treeLevel=="0"){
            $("#add").attr("disabled","disabled");
        }else if(treeLevel=="2"){
            $("#add").removeAttr("disabled");
        }
    };
    //按钮权限
    var setCheckbox=function(){
        $("input[name='cb'],input[name='cball']").change(function(){
            var ids = base.getChecks("cb").val;
            if(ids.length==0){
                $("#modify,#view,#delete,#submit,#flowPath,#publish,#cancel").attr("disabled","disabled");
                if(level){
                    $("#add").removeAttr("disabled");
                }
            }else if(ids.length==1){
                $("#modify,#view,#delete,#submit,#flowPath,#publish,#cancel").removeAttr("disabled");
                if(level){
                    $("#add").attr("disabled","disabled");
                }
            }else {
                $("#delete,#submit,#publish,#cancel").removeAttr("disabled");
                $("#modify,#view,#flowPath").attr("disabled","disabled");
                if(level){
                    $("#add").attr("disabled","disabled");
                }
            }
        })
    };
    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            base.page.load({
                url:$.base+"/catalogingManager/resourceAdd"
            })
        })
        /*修改*/
        $("#modify").unbind("click").on("click",function(){
            var state = $("input[type='checkbox']:checked").parents("tr").find(".resciState").val();
            if(state!=-1 && state!=0 && state!=6){
                base.requestTip({position:"center"}).error("非待提交、已拒绝不可修改！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            base.page.load({
                url:$.base+"/catalogingManager/updatePage"
            })
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            // var state = $("input[type='checkbox']:checked").parents("tr").find(".resciState").val();
            // if(state!="null" && state!=0 && state!=6 ){
            //     base.requestTip({position:"center"}).error("非待提交、已拒绝不可修改！");
            //     return false;
            // }
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            base.page.load({
                url:$.base+"/catalogingManager/resourceView"
            })
        })
        /*查看流程*/
        $("#flowPath").unbind("click").on("click",function(){
            var state = $("input[type='checkbox']:checked").parents("tr").find(".resciState").val();
            if(state == "0" || state == "null"){
                base.requestTip({position:"center"}).error("待提交不可查看流程！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/catalogingManager/resourceProcess")
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var pass = true;
            $("tbody input[type='checkbox']:checked").each(function (i,o) {
                if($(o).parents("tr").find(".resciState").val() == 4){
                    pass = false;
                    return false;
                }
            })
            if(!pass){
                base.requestTip({position:"center"}).error("已发布状态下不可删除！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            deleGridRows(ids);
        });
        /*发布*/
        $("#publish").unbind("click").on("click",function(){
            var pass = true;
            $("tbody input[type='checkbox']:checked").each(function (i,o) {
                if($(o).parents("tr").find(".resciState").val() != 3){
                    pass = false;
                    return false;
                }
            })
            if(!pass){
                base.requestTip({position:"center"}).error("非待发布状态不可发布！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            publishRows(ids);
        });
        /*提交*/
        $("#submit").unbind("click").on("click",function(){
            var pass = true;
            $("tbody input[type='checkbox']:checked").each(function (i,o) {
                if($(o).parents("tr").find(".resciState").val() != 0){
                    pass = false;
                    return false;
                }
            })
            if(!pass){
                base.requestTip({position:"center"}).error("非待提交状态不可提交！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            submitRows(ids);
        });
        /*撤销*/
        $("#cancel").unbind("click").on("click",function(){
            var pass = true;
            $("tbody input[type='checkbox']:checked").each(function (i,o) {
                if($(o).parents("tr").find(".resciState").val() != 4){
                    pass = false;
                    return false;
                }
            })
            if(!pass){
                base.requestTip({position:"center"}).error("非已发布状态不可撤销！");
                return false;
            }
            var ids = base.getChecks("cb").val;
            cancelRows(ids);
        });
    };
    var setTreeButton=function(){
        // 搜索按钮
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
        treeUpdate($("#treeId").val());//树刷新
        drawGrid();
    };

    /*表格删除操作*/
    function deleGridRows(ids){
        var str = "";
        $.each(ids,function(i,o){
            str += o +",";
        })
        str = str.substring(0,str.length-1);
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/catalogingManager/deleteCatalogUE",
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
    /*表格发布操作*/
    function publishRows(ids){
        var arr = [];
        $.each(ids,function(i,o){
            arr.push({"resciState":4,"id":o});
        })
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定发布吗？</div>",
            confirmCallback:function(){
                common.addLoadBg()
                common.ajax({
                    url:$.base+"/catalogingManager/stateChange",
                    type:"post",
                    params:arr,
                    success:function(data){
                        base.requestTip({position:"center"}).success("发布成功！");
                        common.overLoadBg();
                        refresh();
                    }
                });
            }
        });
    }
    /*表格提交操作*/
    function submitRows(ids){
        var arr = [];
        $.each(ids,function(i,o){
            arr.push({"resciState":1,"id":o});
        })
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定提交吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url: $.base + "/catalogingManager/stateChange",
                    type: "post",
                    params: arr,
                    success:function(data){
                        base.requestTip({position:"center"}).success("提交成功！");
                        refresh();
                    }
                });
            }
        });
    }
    /*表格撤销操作*/
    function cancelRows(ids){
        var arr = [];
        $.each(ids,function(i,o){
            arr.push({"resciState":5,"id":o});
        })
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定撤销吗？</div>",
            confirmCallback:function(){
                common.addLoadBg()
                common.ajax({
                    url: $.base + "/catalogingManager/stateChange",
                    type: "post",
                    params: arr,
                    success:function(data){
                        base.requestTip({position:"center"}).success("撤销成功！");
                        common.overLoadBg();
                        refresh();
                    }
                });
            }
        });
    }
    var loading = function () {
        // 主题分类
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            params:{"classId":"5"},
            type:"post",
            success:function (d) {
                $.each(d,function(i,o){
                    $("#topicId").append("<option value='"+o.id+"'>"+o.itemName+"</option>")
                })
            }
        })
        // 行业分类
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:"post",
            params:{"classId":"4"},
            success:function (d) {
                $.each(d,function(i,o){
                    $("#industryId").append("<option value='"+o.id+"'>"+o.itemName+"</option>")
                })
            }
        })
        //OID标识
        base.ajax({
            url:$.base+"/resciPublish/queryOID",
            type:"post",
            success:function(d){
                $.each(d,function (i,o) {
                    $("#oid").append("<option value='"+o.oid+"'>"+o.oid+"</option>");
                })
            }
        })
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
    }
    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            getTree("1");
            setTreeButton();
            loading();
        },
        refresh:function(){refresh();}
    };
});


