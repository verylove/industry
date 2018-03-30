define(["base","app/commonApp","app/form","date5.0"],function(base,common,conditions,laydate){
    var grid = null;
    var gridOption = null;
    //获取动态表单,以及生成表头
    var condition = function(node){
        $.ajax({
            url:$.base+"/resciRegister/initResciPage",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"catalogueId":node[0].id}),
            "success":function(result){
                if(result.query && result.query.length>0){
                    //查询条件
                    var html = conditions.form(result.query,true);
                    $("#gridForm").empty().html(html).append(
                        "<div class='form-group'>" +
                        "<a class='btn btn-info blue' id='buttonSearch'><i class='fa fa-search'></i><span>查询</span></a>" +
                        "<a class='btn btn-info blue' id='reset'><i class='fa fa-undo'></i><span>重置</span></a>" +
                        "</div>"
                    );
                   conditions.initDate();
                }
                //表头
                if(result.show && result.show.length>0){
                    var arr=[];
                    var ths="";
                    $.each(result.show,function(index,item){
                        var obj={};
                        obj.data = item.columnName;
                        obj.width = Math.floor(100/result.show.length) +"%";
                        arr.push(obj);
                        ths+="<th>"+item.attributeName+"</th>"

                    });
                }
                $("#grid thead tr").html(ths);
                if(arr && arr.length>0){
                    setGridOption(node,arr);
                    drawGrid();
                    $(".ui-content-buttonbar").show();
                    var topHeight=$("#rightPage .ui-content-toolsbar").height();
                    $("#rightPage .ui-content-aside").css("top",topHeight>140?topHeight:'140px');
                }else{
                    $(".ui-content-buttonbar").hide();
                }
                buttonSearch();
                reset();
            }
        })
    };
    var setGridOption = function(node,arr){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/resciRegister/listResci",
            type:"post",
            contentType:"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"));
                param.catalogueId = node[0].id;
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };
        gridOption.columns=arr;
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                return "<div class='findCatalague' catalagueId='"+rowData.CATALOGUE_ID+"'>"+rowNumber+"</div>"
            },
                "targets":0
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            setCheckbox();
        }
    };
    var treeObj = null;
    /**设置树组件**/
    var getTree = function(defaultId){
        treeObj = common.tree({
            container:$("#treebar"),
            url:$.base+"/resciRegister/resciRegisteTree",
            type:"post",
            defaultId:defaultId?defaultId:null,
            callback:{
                beforeClick:function (event,containerId,treeData) {
                    if(containerId.level == 0 || containerId.level==1 ||containerId.level==2){
                        return false;
                    }
                },
                onClick:function(event,containerId,treeData){
                    if(!treeData.isParent){ //非父节点的时候才进行切换表格
                        condition(treeObj.getSelectedNodes());
                        //drawGrid();
                    }
                    $("#treeId").attr("rid",treeData.id);
                }
            }
        });
        //将第一个节点选中,判断
        var nodes = treeObj.getNodes();
        //var level=0;
        //findLevel(level,nodes);
       // debugger
        defaultId==1?checkNode(nodes,treeObj):"";
        treeObj.getSelectedNodes().length>0?condition(treeObj.getSelectedNodes()):"";
    };
    //循环找出最level数值最大的
    // function findLevel(level,nodes){
    //    $.each(nodes,function (i,node) {
    //        node.level> level ? level=node.level:level=level;
    //        node.children?findLevel(level,node.children):"";
    //        return level
    //    })
    // }
    //默认选中第一个树的节点
    function checkNode(nodes,treeObj){
        //nodes开始，nodes的层级是0，
        if(nodes.length>0){
            if(nodes[0].isParent){
                checkNode(nodes[0].children,treeObj);
            }else{
                treeObj.selectNode(nodes[0]);
            }
        }
    }
    var treeUpdate = function(treeId){
        if(treeObj){
            treeObj.destroy();
        }
        getTree(treeId);
    };
    var drawGrid = function(treeId){
        if(grid){
            //将thead获取出来
            grid.destroy();
            var thead = $("#grid thead").html();
            $("#grid").html("").append("<thead>"+thead+"</thead>");

        }
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox",
            idDef:"CiId"
        });
        $("#add").removeAttr("disabled");
        $("#edit,#deleteBatch,#view").attr("disabled","disabled");
        loading();
    };
    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*新增*/
        $("#add").unbind("click").on("click",function(){
            $("#treeId").val(treeObj.getSelectedNodes()[0]["id"]);
            common.load($.base+"/resciRegister/addResciRegisterView");
        });
        /*编辑*/
        $("#edit").unbind("click").on("click",function(){
            $("#treeId").val(treeObj.getSelectedNodes()[0]["id"]);
            var ciId = $("[name='cb']:checked").attr("value");
            var ids = base.getChecks("cb").val;
            $("#checkValue").val(ciId);
           /* var catalogueId ="";
            //获取被选中的
            $.each(ids,function (index,item) {
                catalogueId+= $("[value='"+item+"']:checked").parents("tr").find(".findCatalague").attr("catalagueId")+","
            });*/
            editCheckOccupied(ids);
        });
        $("#view").unbind("click").on("click",function(){
            $("#treeId").val(treeObj.getSelectedNodes()[0]["id"]);
            var ciId = $("[name='cb']:checked").attr("value");
            $("#checkValue").val(ciId);
            common.load($.base+"/resciRegister/resciRegisterDetail");
        });
        /*批量删除*/
        $("#deleteBatch").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            // var catalogueId ="";
            //获取被选中的
            /*$.each(ids,function (index,item) {
                catalogueId+= $("[value='"+item+"']:checked").parents("tr").find(".findCatalague").attr("catalagueId")+","
            });*/
            // $(".cb:checked").each(function(index,item){
            //     //将选中的条目catalogueId选出来
            //     catalogueId+=$(item).parents("tr").find("findCatalague").attr("catalagueId")+","
            // });
             // $(".findCatalague:checked").each(function (index,item) {
             //     catalogueId+=$(item).attr("catalagueId")+","
             // });
            checkOccupied(ids);
        });

        /*导出*/
        $("#export").unbind("click").on("click",function(){
            var catalogueId = treeObj.getSelectedNodes()[0]["id"];
            var ids = base.getChecks("cb").val;
            var param = JSON.stringify(base.form.getParams($("#gridForm")));
            window.location.href = $.base+"/exportController/resciRegisterExport?ids="+ids+"&catalogueId="+catalogueId+"&type=1&param="+param;
        });

        /*导入*/
        $("#import").unbind("click").on("click",function(){
            var catalogueId = treeObj.getSelectedNodes()[0]["id"];
            var modal = base.modal({
                width:700,
                height:270,
                label:"资源登记导入",
                modalOption:{"backdrop":"static","keyboard":false},
                url:$.base+"/exportController/resciRegisterImportPage?catalogueId="+catalogueId,
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

    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        treeUpdate($("#treeId").attr("rid"));//树刷新
        drawGrid();//表格刷新
    };
    /*判断是否被占用*/
    function checkOccupied(id){
        var ids = id.join();
        $.ajax({
            url:$.base+"/resciRegister/checkDeleteResciRegister",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"CiId":ids,"catalogueId":treeObj.getSelectedNodes()[0]["id"]}),
            success:function(data){
                if(data.success){ //可删除资源
                    deleGridRows(base.getChecks("cb").val,treeObj.getSelectedNodes()[0]["id"]);
                }else{//不可删除资源
                    base.requestTip({position:"center"}).error(data.message);
                }
            }
        });
    }
    /*判断编辑的条目是否有编辑的权利*/
    function editCheckOccupied(id){
        var ids=id.join();
        $.ajax({
            url:$.base+"/resciRegister/checkUpdateResciRegister",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"CiId":ids,"catalogueId":treeObj.getSelectedNodes()[0]["id"]}),
            success:function(data){
                if(data.success){ //可修改资源
                    common.load($.base+"/resciRegister/updateResciRegisterView");
                }else{//不可修改资源
                    base.requestTip({position:"center"}).error(data.message);
                }

            }
        });
    }
    /*表格删除操作*/
    function deleGridRows(arr,id){
        //接收的是arr，转换成字符串拼接
        var ids = arr.join();
        base.confirm({
            label:"提示",
            text:"<div style='text-align:center;font-size:13px;'>确定删除此资源？</div>",
            confirmCallback:function(){
                common.ajax({
                    url:$.base+"/resciRegister/deleteResciRegister",
                    type:"post",
                    //contentType:"appliction/json",
                    params:{"id":ids,"catalogueId":id},
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
                $("#edit,#deleteBatch,#view").removeAttr("disabled");
            }else if(ids.length>1){
                $("#add,#edit,#view").attr("disabled","disabled");
                $("#deleteBatch").removeAttr("disabled");
            }else{
                $("#add").removeAttr("disabled");
                $("#edit,#deleteBatch,#view").attr("disabled","disabled");
            }
        })
    };
    //搜素
    function buttonSearch(){
        $("#buttonSearch").off().on("click",function () {
            drawGrid();
        })
    }
    //重置
    function reset(){
        $("#reset").off().on("click",function () {
            $("input,select").val("");
            drawGrid();//表格刷新
        })
    }
    function loading() {
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
            getTree("1");
            loading();
        },
        refresh:function(){refresh();}
    };
});


