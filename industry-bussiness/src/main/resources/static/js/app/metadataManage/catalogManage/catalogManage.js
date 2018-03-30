define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/catalogManager/listCatalog",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"));
                param.fieldId = $("#treeId").attr("rid");
                param.comId=$("#comName").attr("rid");
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
            { "data": "catalogName","sWidth":"18%"},
            { "data": "catalogCode","sWidth":"18%"},
            { "data": "comName","sWidth":"18%"},
            { "data": "userName","sWidth":"18%"},
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
            if($("#treeId").attr("rid")=="1"){
                $("#add").attr("disabled",true);
            }else{
                $("#add").removeAttr("disabled");
            }
            $("#edit,#delete,#view").attr("disabled","disabled");
        }
    };
    var treeObj = null;
    /**设置树组件**/
    var getTree = function(defaultId,params){
        var params = params?params:{};
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/catalogManager/catalogTree",
            params:params,
            defaultId:defaultId?defaultId:null,
            callback:{
                onClick:function(event,containerId,treeData){
                    $("#treeId").attr("rid",treeData.id);
                    drawGrid();
                    if(treeData.id=="1"){
                        $("#import,#add").attr("disabled",true);
                    }else{
                        $("#import,#add").attr("disabled",false);
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
        if($("#treeId").attr("rid")=="1"){
            $("#add").attr("disabled",true);
        }else{
            $("#add").removeAttr("disabled");
        }
        $("#edit,#delete,#view").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            common.load($.base+"/catalogManager/catalogAdd");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/catalogManager/catalogModify");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function () {
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/catalogManager/catalogView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            checkOccupied(ids);
        });

        /*导出*/
        $("#export").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            var fieldId = $("#treeId").attr("rid");
            var params = base.form.getParams($("#gridForm"));
            window.location.href = encodeURI(encodeURI($.base+"/exportController/catalogExport?ids="+ids+"&fieldId="+fieldId+"&type=1&catalogName="+(params.catalogName?params.catalogName:'')+"&catalogCode="+(params.catalogCode?params.catalogCode:'')+"&comId="+($("#comName").attr("rid")?$("#comName").attr("rid"):'')+"&userId="+(params.userId?params.userId:'')));
        });

        /*导入*/
        $("#import").unbind("click").on("click",function(){
            var modal = base.modal({
                width:700,
                height:270,
                label:"目录导入",
                modalOption:{"backdrop":"static","keyboard":false},
                url:$.base+"/exportController/catalogExportPage",
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
            url:$.base+"/catalogManager/deleteCatalogCheck",
            type:"post",
            params:{"ids":str},
            success:function(data){
                if(data.success){
                    deleGridRows(str);
                }else{
                    base.requestTip({position:"center"}).error("目录被引用，不允许删除！");
                }
            }
        });
    }

    /*搜索按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"));
            $("#comName").attr("rid","")
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
            }else if($("#treeId").attr("rid")=="1"){
                $("#edit,#delete,#view,#add").attr("disabled","disabled");
            }else {
                $("#add").removeAttr("disabled");
                $("#edit,#delete,#view").attr("disabled","disabled");
            }
        })
    };
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        treeUpdate($("#treeId").attr("rid"));//树刷新
        drawGrid();//表格刷新
    };

    /*表格删除操作*/
    function deleGridRows(str){
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/catalogManager/deleteCatalog",
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
    // 初始化赋值
    var loading = function(){
        //目录审核员
        base.ajax({
            url:$.base+"/userManager/listUser",
            type:"post",
            params:{"pageNo":1,"pageSize":0,"param":{
                "roleId":7
            }},
            success:function (d) {
                $.each(d.data,function (i,o) {
                    $("#gridForm select[name='userId']").append("<option value='"+o.userId+"'>"+o.userName+"</option>")
                })
            }
        })
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
                        $("#comName").attr("rid",treeNode.id);
                    }
                });
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


