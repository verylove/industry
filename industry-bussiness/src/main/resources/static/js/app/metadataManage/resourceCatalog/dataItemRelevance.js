define(["base","app/commonApp"],function(base,common){
    var gridRele = null;
    var gridOptionRele = null;
    var setGridOptionRele = function(){
        gridOptionRele = common.getGridOption();
        gridOptionRele.ajax = {
            url:$.base+"/dataManager/listData",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:{
                        enabledState:0,
                        establishType:0,
                        dataName:$(".dataName[name='dataName']").val(),
                        versionId:$(".versionId[name='versionId']").val(),
                        constraint:'O'
                    }
                };
                return JSON.stringify(d);
            }
        };

        gridOptionRele.columns = [
            { "data": "id","sWidth":"8%"},
            { "data": "dataName","sWidth":"15%"},
            { "data": "dataEname","sWidth":"15%"},
            { "data": "versionName","sWidth":"15%"},
            { "data": "dataType","sWidth":"15%"},
            { "data": "dataLength","sWidth":"15%"},
            { "data": "dictionarName","sWidth":"17%"}
        ];
        gridOptionRele.columnDefs = [
            {"render":function(data,t,row,o){
                return o.row+1+"<input type='hidden' class='obj' value='"+JSON.stringify(row)+"'>"
            },
                "targets":0
            }
        ];
        gridOptionRele.drawCallback = function(setting){
            setGridButton();
        }
    };
    var drawGridRele = function(){
        if(gridRele){gridRele.destroy();}
        gridRele = common.drawGrid({
            container:$("#gridRelevance"),
            gridOption:gridOptionRele,
            selectType:"checkbox",
            // idDef:'roleId'
        });
    };
    var setGridButton = function () {
        $("#searchRele").on("click",function () {
            drawGridRele();
        })
    }
    var loading = function () {
        // 版本号
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:"post",
            params:{"classId":3},
            success:function (d) {
                $.each(d,function (i,o) {
                    $(".versionId[name='versionId']").append("<option value='"+o.id+"'>"+o.itemName+"</option>")
                })
            }
        })
    }
    return {
        main:function(){
            setGridOptionRele();
            drawGridRele();
            loading();
        }
    };
});
