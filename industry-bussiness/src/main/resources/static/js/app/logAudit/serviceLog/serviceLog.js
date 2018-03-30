define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/serviceManager/listServiceLog",
            type:"post",
            contentType:"application/json",
            data:function(d){

                var tim="",start="",end="";
                var param = base.form.getParams($("#gridForm"));
                if(!$.isEmptyObject(param)){
                    var tim="",start="",end="";
                    if(param.responseTime){
                        tim = param.responseTime.split("~");
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
            { "data": "id","sWidth":"8%"},
            { "data": "serviceName","sWidth":"20%"},
            { "data": "ip","sWidth":"15%"},
            { "data": "maintenance","sWidth":"15%"},
            { "data": "requestTime","sWidth":"15%"},
            { "data": "responseTime","sWidth":"12%"},
            { "data": "result","sWidth":"15%"}
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
    var drawGrid = function(){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption,
            selectType:"checkbox"
        });
    };

    /***设置查询***/
    var setSearch = function(){
        $("#requestSearch").on("click",function(){
            common.search(grid);
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"));
            drawGrid();
        });
        $("#export").on("click",function(){
            exportDiary();
        });
    };

    /****导出EXCEL****/
    var exportDiary = function () {
        $("#export").unbind("click").on("click",function(){
            var ids = base.getChecks("cb").val;
            window.location.href = $.base+"/exportController/serviceLogExport?ids="+ids+"&type=1";
        });
    }

    return {
        main:function(){
            common.pull();
            common.init();
            setGridOption();
            drawGrid();
            setSearch();
        }
    };
});


