define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/operationManager/listOperationLog",
            type:"post",
            contentType:"application/json",
            data:function(d){

                var tim="",start="",end="";
                var param = base.form.getParams($("#gridForm"));
                if(!$.isEmptyObject(param)){
                    var tim="",start="",end="";
                    if(param.createTime){
                        tim = param.createTime.split("~");
                        start=$.trim(tim[0]);
                        end=$.trim(tim[1]);
                        param.createTimeStart=start;
                        param.createTimeEnd=end;
                    }
                }
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };
        gridOption.columns = [
            { "data": "id","sWidth":"5%"},
            { "data": "moduleName","sWidth":"20%"},
            { "data": "userName","sWidth":"15%"},
            { "data": "ip","sWidth":"20%"},
            { "data": "createTime","sWidth":"20%"},
            { "data": "result","sWidth":"20%"}
        ];
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                return row.row+1;
            },
                "targets":0
            }
        ];
        gridOption.drawCallback = function(setting){
        }
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption
        });
    };
    /*刷新右侧表格*/
    var refresh = function(){
        drawGrid();//表格刷新
    };

    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            drawGrid();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"));
            drawGrid();
        });
    }

    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            setTreeButton();
            drawGrid();
        }
    };
});


