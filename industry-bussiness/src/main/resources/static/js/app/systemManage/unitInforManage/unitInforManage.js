define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(ids){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/organizationManager/listOrganization",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param =  base.form.getParams($("#gridForm"));
                param.comId = $("#treeId").val();
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };
        gridOption.columns = [
            { "data": "comName","sWidth":"14%"},
            { "data": "pidName","sWidth":"14%"},
            { "data": "note","sWidth":"14%"},
            { "data": "contact","sWidth":"14%"},
            { "data": "contactPhone","sWidth":"14%"},
            { "data": "address","sWidth":"15%"},
            { "data": "email","sWidth":"15%"}
        ];
        gridOption.columnDefs = [
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
            $("#add").removeAttr("disabled");
            $("#edit,#delete,#view").attr("disabled","disabled");
        }
    };
    var treeObj = null;
    /**设置树组件**/
    var getTree = function(defaultId,params){
        var params = params?params:{};
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/organizationManager/listOrganizationTree",
            params:params,
            defaultId:defaultId?defaultId:null,
            callback:{
                onClick:function(event,containerId,treeData){
                    $("#treeId").val(treeData.id)
                    drawGrid();
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
            selectType:"checkbox",
            idDef:"comId"
        });
        $("#add").removeAttr("disabled");
        $("#edit,#delete,#view").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            common.load($.base+"/organizationManager/addOrganizationView");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/organizationManager/editOrganizationView");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/organizationManager/queryOrganizationView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            deleGridRows(ids);
        });

    };
    /*搜索、重置按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"))
            drawGrid();
        });
    }
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        treeUpdate($("#treeId").val(),{});//树刷新
        drawGrid();//表格刷新
    };
    /*表格删除操作*/
    function deleGridRows(ids){
        var str = '';
        $.each(ids,function(i,o){
            str += o +",";
        })
        str = str.substring(0,str.length-1);
        var isPass = true;
        base.ajax({
            url:$.base+"/organizationManager/checkdeleteOrganization",
            type:"post",
            params:{"comIds":str},
            async:false,
            success:function(d){
                if(!d.success){
                    isPass = false;
                    base.requestTip({position:"center"}).error(d.message);
                }
            }
        })
        if(!isPass){
            return false;
        }
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/organizationManager/deleteOrganization",
                    type:"post",
                    params:{"comIds":str},
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
    var loading = function () {
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
