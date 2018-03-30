define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/userManager/listUser",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"))
                param.comId = $("#gridForm #comName").attr("tid")
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };
        gridOption.columns = [
            { "data": "login","sWidth":"14%"},
            { "data": "userName","sWidth":"14%"},
            { "data": "roleName","sWidth":"14%"},
            { "data": "comName","sWidth":"15%"},
            { "data": "officePhone","sWidth":"15%"},
            { "data": "email","sWidth":"14%"},
            { "data": "lockStatus","sWidth":"14%"}
        ];
        gridOption.columnDefs = [
            {"render":function(data,type,rowData,row){
                var html = "";
                if(rowData.userId == 1){
                    html = '<button class="btn blue btn-info" disabled="disabled"><span>正常</span></button>'
                }else {
                    var but = data==1?"锁定":"正常";
                    var color = data==1?"red":"blue";
                    html = '<button class="btn lockStatus '+color+' btn-info" lockSta="'+rowData.lockStatus+'" usId="'+rowData.userId+'"><span>'+but+'</span></button>'
                }
                return html;
            },
                "targets":6
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
            $("#add").removeAttr("disabled");
            $("#edit,#delete,#view,#resetPass").attr("disabled","disabled");
        }
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            idDef:"userId",
            gridOption:gridOption,
            selectType:"checkbox",
        });
        $("#add").removeAttr("disabled");
        $("#edit,#delete,#view,#resetPass").attr("disabled","disabled");
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        // 控制按钮灰置
        $("input[name='cb'],input[name='cball']").on("change",function(){
            var ids = base.getChecks("cb").val;
        })
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            common.load($.base+"/userManager/addUserView");
        })
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/userManager/userEditView");
        })
        /*查看*/
        $("#view").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/userManager/userDetailView");
        })
        /*重置密码*/
        $("#resetPass").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            $("#rowId").attr("rid",ids[0]);
            common.load($.base+"/userManager/resetPassView");
        })
        /*批量删除*/
        $("#delete").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            deleGridRows(ids);
        });
    };
    /*树搜索,重置按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"))
            $("#gridForm #comName").attr("tid",'');
            drawGrid();
        });
    }
    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        drawGrid();//表格刷新
    };
    /*表格删除操作*/
    function deleGridRows(ids){
        var str = ''
        $.each(ids,function(i,o){
            str+=o+','
        });
        str = str.substring(0,str.length-1);
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/userManager/deleteUser",
                    type:"post",
                    params:{"userIds":str},
                    success:function(data){
                        base.requestTip({position:"center"}).success("删除成功！");
                        refresh();
                    }
                });
            }
        });
    }
    var load =  function(){
        // 角色信息
        base.ajax({
            url:$.base+"/roleManager/queryRole",
            type:'post',
            success:function(d){
                $.each(d,function(i,o){
                    $("#roleName").append('<option value="'+o.roleId+'">'+o.roleName+'</option>')
                })
            }
        })
        //单位信息
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
        // 锁定与解锁
        $("#grid").on("click",".lockStatus",function () {
            var message = "";
            var userId = $(this).attr("usId");
            var lockSta = $(this).attr("lockSta");
            if(lockSta==1){
                message="确认解锁该用户吗？";
            }else {
                message="确认锁定该用户吗？";
            }
            lockSta = lockSta==1?2:1;
            base.confirm({
                label:"提示",
                text:"<div style='text-align:center;font-size:13px;'>"+message+"</div>",
                confirmCallback:function(){
                    common.ajax({
                        url:$.base+"/userManager/updateStatus",
                        type:"post",
                        params:{"userId":userId,"lockStatus":lockSta},
                        success:function(data){
                            refresh();
                        }
                    });
                }
            });
        })
    }

    //按钮权限
    var setCheckbox=function(){
        $("input[name='cb'],input[name='cball']").change(function(){
            var ids = base.getChecks("cb").val;
            if(ids.length==1){
                $("#add,#edit,#delete,#view,#resetPass").attr("disabled","disabled");
                if(ids[0]=="1"){
                    $("#view").removeAttr("disabled");
                    // base.requestTip({position:"center"}).error("admin不可修改删除！");
                }else{
                    $("#edit,#delete,#view,#resetPass").removeAttr("disabled");
                }
            }else if(ids.length>1){
                $("#add,#edit,#view,#resetPass,#view").attr("disabled","disabled");
                if(ids.indexOf("1")>-1){
                    $("#delete").attr("disabled","disabled");
                    // base.requestTip({position:"center"}).error("admin不可删除！");
                }else{
                    $("#delete").removeAttr("disabled");
                }
            }else{
                $("#add").removeAttr("disabled");
                $("#edit,#delete,#view,#resetPass").attr("disabled","disabled");
            }
        });
    };

    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            setTreeButton();
            drawGrid();
            load();
        },
        refresh:function(){refresh();}
    };
});


