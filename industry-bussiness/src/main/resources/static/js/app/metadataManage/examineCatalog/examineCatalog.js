define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/catalogingManager/examineCatalogList",
            type:"post",
            contentType:"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"))
                var catalogId = $("#treeId").val();
                param.catalogId = catalogId == 1?"":catalogId;
                param.resciState = 1;
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
                return row.row+1;
            },
                "targets":0
            },
            {"render":function(data,type,rowData,row){
                var html = "";
                switch (data){
                    case 0:
                        html = "已保存";
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
            $("#add,#view").attr("disabled","disabled");
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
                        $("#treeId").val(treeData.id)
                        drawGrid();
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
        $("#add,#view").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*审批*/
        $("#add").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/catalogingManager/catalogExamine");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/catalogingManager/catalogView");
        })
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
        drawGrid();//表格刷新
    };

    //按钮权限
    var setCheckbox=function(){
        $("input[name='cb'],input[name='cball']").change(function(){
            var ids = base.getChecks("cb").val;
            if(ids.length==1){
                $("#add,#view").removeAttr("disabled");
            }else{
                $("#add,#view").attr("disabled","disabled");
            }
        })
    };

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


