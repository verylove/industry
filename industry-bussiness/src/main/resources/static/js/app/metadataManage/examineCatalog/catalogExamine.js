define(["base","app/commonApp","app/metadataManage/examineCatalog/examineCatalog"],function(base,common,examineCatalog){
    var steps = null;
    /**设置步骤组件**/
    var setSteps = function(type){
        steps = base.steps({
            container:$("#ui-steps"),
            height:common.getStepsHeight(),
            data:[
                {"label":"查看编目申请","contentToggle":"#content1"},
                {"label":"查看数据元信息","contentToggle":"#content2","callback":function(){}},
                {"label":"填写审批意见","contentToggle":"#content3","callback":function(){}}
            ],
            buttons:[
                {label:"测试连接","cls":"btn btn-info blue testConnect hidden","callback":function(a){
                }},
                {"type":"back","cls":"btn btn-info blue","icon":"ion-chevron-left","callback":function(a){
                    runPrev("1");
                }},
                {"type":"forward","cls":"btn btn-info blue","icon":"ion-chevron-right","iconPosition":"last","callback":function(a){
                    runNext("1");
                }},
                {"type":"confirm","cls":"btn btn-info blue","icon":"fa fa-check","callback":function () {
                    var isPass = base.form.validate({form:$("#form3"),checkAll:true});
                    if(!isPass){return ;}
                    base.ajax({
                        url:$.base+"/catalogingManager/review",
                        params:{"cataUEId":$("#rowId").attr("rid"),"examineStep":3,"examineContent":$("#form3 [name='examineContent']").val(),"examineState":$("#form3 [name='examineState']").val()},
                        type:"post",
                        success:function (d) {
                            base.page.back({
                                callback:function(){
                                    if(examineCatalog){
                                        examineCatalog.refresh();
                                    }
                                }
                            });
                        }
                    })
                }}
            ]
        });
    }


    //上一步，下一步
    var runNext= function (params) {
        steps.forward(function(){
            var step = steps.getStep();
            switch(step){
                case 0:
                case 1:
                case 2:
                case 3:
                    return true;
                    break;
            }
        })
    }

    var runPrev = function (params){
        steps.back(function(){
            var step = steps.getStep();
            switch(step){
                case 1:
                case 2:
                    return true;
                    break;
                default: return true;
                    break;
            }
        })
    }
    var loading = function(){
        base.ajax({
            url:$.base+"/catalogingManager/review",
            params:{"cataUEId":$("#rowId").attr("rid"),examineStep:0},
            type:"post",
            success:function (d) {
                base.form.init(d.catalogUEMap,$("#form1"))
                $("#form2 img").attr("src",$.base+"/"+d.catalogUEMap.imagePath);
                base.form.init(d.catalogUEMap,$("#form2"))
                $.each(d.dataElmentList,function (i,o) {
                    var i = i+1
                    var html = "<tr>"+
                    "<td>"+i+"</td>"+
                    "<td>"+o.dataName+"</td>"+
                    "<td>"+o.dataEname+"</td>"+
                    "<td>"+o.dataCode+"</td>"+
                    "<td>"+o.dataType+"</td>"+
                    "<td>"+o.dataLength+"</td>"+
                    "</tr>"
                    $("#form2 #grid tbody").append(html)
                })
            }
        })
    }

    return {
        main:function(){
            setSteps();
            common.init();
            loading();
        }
    };
});