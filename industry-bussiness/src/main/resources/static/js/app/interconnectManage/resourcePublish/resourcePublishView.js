define(["base","app/commonApp","app/interconnectManage/resourcePublish/resourcePublish"],function(base,common,resourcePublish){

    function timestampToTime(timestamp) {
        var date = new Date(timestamp );//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = date.getDate() + ' ';
        h = date.getHours() + ':';
        m = date.getMinutes() + ':';
        s = date.getSeconds();
        return Y+M+D+h+m+s;
    }
    /*元数据详情*/
    var setMetaData=function(){
        common.ajax({
            url:$.base+"/resciPublish/queryResciCatalogueByCatalogueId",
            type:"post",
            params:{"id":$("#rowId").val()},
            success:function(d){
                base.form.init(d,$("#dataItemForm"));
                $(".itemImg").attr("src",$.base+'/'+d.imagePath);
                $(".header").text(d.resciName);
                $(".description").text(d.resciContent?d.resciContent:'--');
                $("#submitTime").text(d.createTime==null?"--":d.createTime);//最后更新时间
                $("#releaseTime").text(timestampToTime(d.releaseTime));//发布时间
            }
        });
    }

    /*数据项信息*/
    var setDataItem=function(){
        common.ajax({
            url:$.base+"/resciPublish/queryDataElementByCatalogueId",
            type:"post",
            params:{"id":$("#rowId").val()},
            success:function(d){

                $(".dataItemTab").empty();
                var strs="";
                if(d.length>0){
                    $.each(d,function(i,v){
                        strs+="<tr><td>"+(i+1)+"</td><td>"+v.dataName+"</td><td>"+v.dataEname+"</td><td>"+v.widgetName+"</td><td>"+v.dataLength+"</td><td>"+v.dataCode+"</td></tr>";
                    });
                    $(".dataItemTab").append(strs);
                }
            }
        });
    }

    /*加载数据示例-获取表头-渲染表格*/
    var condition = function(){
        $.ajax({
            url:$.base+"/resciRegister/initResciPage",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"catalogueId":$("#rowId").val()}),
            success:function(result){
                //表头
                if(result.show && result.show.length>0){
                    var arr=[];
                    var ths="";
                    $.each(result.show,function(index,item){
                        var obj={};
                        obj.data = item.columnName;
                        arr.push(obj);
                        ths+="<th>"+item.attributeName+"</th>"
                    });
                }
                $("#grid11 thead tr").html(ths);
                setGridOption(arr);
                drawGrid();
            }
        })
    };
    var gridOption = null;
    var grid = null;
    var setGridOption = function(arr){
        gridOption = common.getGridOption();
        var indexcolumn;
        $.each(arr,function(i,v){
           if(v.data=="COL_SJY00002"){
               indexcolumn=i
           };
        });
        gridOption.ajax = {
            url:$.base+"/resciRegister/listResci",
            type:"post",
            contentType:"application/json",
            data:function(d){
                var param = {};
                param.catalogueId = $("#rowId").val();
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
                return "<a class='view' CiId='"+rowData.CiId+"' catalogueId='"+rowData.CATALOGUE_ID+"'>"+rowData.COL_SJY00002+"</a>";
            },
                "targets":indexcolumn
            }
        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
        }
    };

    var drawGrid = function(){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid11"),
            gridOption:gridOption
        });
    };

    var setGridButton = function(){
        /*查看*/
        $(".view").unbind("click").on("click",function(){
            $("#gridId").attr("CiId",$(this).attr("CiId")).attr("catalogueId",$(this).attr("catalogueId"));
            base.page.load({
                url:$.base+"/resciPublish/resciPublishDataElementView"
            });
        })
    }

    return {
        main:function(){
            common.init();
            setMetaData();
            setDataItem();
            condition();
        }
    };

});
