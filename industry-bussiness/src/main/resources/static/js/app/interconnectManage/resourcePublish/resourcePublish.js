define(["base","app/commonApp"],function(base,common){
    var grid = null;
    var gridOption = null;
    /*时间戳转化成日期格式*/
    function timestampToTime(timestamp) {
        var date = new Date(timestamp );//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + (date.getHours()) + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + (date.getMinutes()) + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds();
        return Y+M+D+h+m+s;
    }

    //单位信息
    var getUnit=function(){
        common.ajax({
            url:$.base+"/organizationManager/listOrganizationTree",
            type:"post",
            success:function(d){
                var treeSelectObj = base.form.treeSelect({
                    container:$("#comId"),
                    data:d,
                    clickCallback:function(event,treeId,treeNode){

                    }
                });
            }
        })
    }

    //行业分类
    var getIndustry=function(){
        common.ajax({
            url:$.base+"/dictionarManager/dict",
            type:"post",
            async:false,
            params:{"classId":4},
            success:function(d){
                $("#industryId").empty();
                $("#industryId").append("<option value=''>全部</option>");
                var val = localStorage.getItem("val");
                var type = localStorage.getItem("type");
                $.each(d,function (i,o) {
                    if(val==o.id && type=="industry"){
                        $("#industryId").append("<option value='"+o.id+"' selected>"+o.itemName+"</option>");
                    }else{
                        $("#industryId").append("<option value='"+o.id+"'>"+o.itemName+"</option>");
                    }
                })

            }
        })
    }

    //主题分类
    var getTheme=function(){
        common.ajax({
            url:$.base+"/dictionarManager/dict",
            type:"post",
            async:false,
            params:{"classId":5},
            success:function(d){
                $("#topicId").empty();
                $("#topicId").append("<option value=''>全部</option>");
                var val = localStorage.getItem("val");
                var type = localStorage.getItem("type");
                $.each(d,function (i,o) {
                    if(val==o.id && type=="topic"){
                        $("#topicId").append("<option value='"+o.id+"' selected>"+o.itemName+"</option>");
                    }else{
                        $("#topicId").append("<option value='"+o.id+"'>"+o.itemName+"</option>");
                    }

                })
            }
        })
    }

    //OID编码
    var getOID=function(){
        common.ajax({
            url:$.base+"/resciPublish/queryOID",
            type:"post",
            success:function(d){
                $("#oid").empty();
                $("#oid").append("<option value=''>全部</option>");
                $.each(d,function (i,o) {
                    $("#oid").append("<option value='"+o.oid+"'>"+o.oid+"</option>");
                })
            }
        })
    }

    var setGridOption = function(){
        gridOption = common.getGridOption();
        gridOption.ajax = {
            url:$.base+"/resciPublish/listResciCatalogue",
            type:"post",
            "contentType":"application/json",
            data:function(d){
                var param = base.form.getParams($("#gridForm"));
                var d = {
                    pageNo:d.start/d.length+1,
                    pageSize:d.length,
                    param:param
                };
                return JSON.stringify(d);
            }
        };
        gridOption.columns = [
            { "data": "id","sWidth":"100%"}
        ];
        gridOption.columnDefs = [
            {"render":function(rowNumber,type,rowData,row){
                    var imgUrl = $.base+'/'+rowData.imagePath;
                    var html = '<div class="publishContent_left" rid="'+rowData.id+'">'+
                                    '<div class="publishContent_left_title">'+rowData.resciName+'</div>'+
                                    '<div class="publish_box">'+
                                    '<img src="'+imgUrl+'" alt="">'+
                                    '<div class="publish_text">'+(rowData.resciContent?rowData.resciContent:"--")+'</div>'+
                                    '<div class="publish_title">'+
                                    '<span>主题分类：</span>'+rowData.topicName+''+
                                '</div>'+
                                '<div class="publish_title">'+
                                    '<span>行业分类：</span>'+rowData.industryName+''+
                                '</div>'+
                                '</div>'+
                                '</div>';
                    return html;
                },
                "targets":0
            },
            {"render":function(rowNumber,type,rowData,row){
                var time=rowData.submitTime==null?"<span'>--</span>":timestampToTime(rowData.submitTime);
                var html = '<div>'+rowData.comName+'</div>';
                return html;
            },
                "targets":1
            },
            {"render":function(rowNumber,type,rowData,row){
                var time=rowData.createTime==null?"<span'>--</span>":rowData.createTime;
                var html = '<div>更新时间：'+time+'</div>';
                return html;
            },
                "targets":2
            }

        ];
        gridOption.drawCallback = function(setting){
            setGridButton();
            localStorage.setItem("val","");
            localStorage.setItem("type","");
        }
    };
    var drawGrid = function(treeId){
        if(grid){grid.destroy();}
        grid = common.drawGrid({
            container:$("#grid"),
            gridOption:gridOption
        });
    };

    /**设置表格各个按钮操作**/
    var setGridButton = function(){
        /*点击行查看详情*/
        $(".setTab tbody tr").unbind("click").on("click",function(){
            $("#rowId").val($(this).find(".publishContent_left").attr("rid"));
            common.load($.base+"/resciPublish/resciPublishDetailsView");
            //校验
            // common.ajax({
            //     url:$.base+"/resciPublish/checkQueryResci",
            //     type:"post",
            //     params:{"catalogueId":$("#rowId").val()},
            //     success:function(d){
            //         if(d.success){
            //             common.load($.base+"/resciPublish/resciPublishDetailsView");
            //         }else{
            //             base.requestTip({position:"center"}).error(d.message);
            //         }
            //     }
            // })

        })
    };

    /*搜索按钮*/
    var setTreeButton=function(){
        $("#search").unbind().on("click",function(){
            refresh();
        });
        $("#reset").unbind().on("click",function(){
            common.reset($("#gridForm"));
            refresh();
        });
    }

    /*刷新左侧树和右侧表格*/
    var refresh = function(){
        drawGrid();//表格刷新
    };

    return {
        main:function(){
            common.pull();
            common.init();
            getUnit();
            getIndustry();
            getTheme();
            //getOID();
            setGridOption();
            drawGrid();
            setTreeButton();
        },
        refresh:function(){refresh();}
    };
});


