define(["base", "app/commonApp", "app/metadataManage/resourceCatalog/resourceCatalog"], function (base, common, resourceCatalog) {
    var resourceObj = {};
    var steps = null;
    /**设置步骤组件**/
    var setSteps = function (type) {
        steps = base.steps({
            container: $("#ui-steps"),
            height: common.getStepsHeight(),
            data: [
                {"label": "填写编目申请", "contentToggle": "#content1"},
                {"label": "填写元数据", "contentToggle": "#content2", "callback": function () {}},
                {"label": "编辑OID标识", "contentToggle": "#content3", "callback": function () {}},
                {"label": "编辑数据项", "contentToggle": "#content4", "callback": function () {}},
                {"label": "编目预览", "contentToggle": "#content5", "callback": function () {}}
            ],
            buttons: [
                {
                    "type": "back",
                    "cls": "btn btn-info blue",
                    "icon": "ion-chevron-left",
                    "callback": function (a) {
                        runPrev();
                    }
                },
                {
                    "type": "forward",
                    "cls": "btn btn-info blue",
                    "icon": "ion-chevron-right",
                    "iconPosition": "last",
                    "callback": function (a) {
                        runNext();
                    }
                },
                {   "label":"取消",
                    "type": "forward",
                    "cls": "btn btn-info orange",
                    "iconPosition": "last",
                    "callback": function () {
                        base.page.back({});
                        $(".mCSB_container").css("left",0);
                    }
                },
                {   label: "取消",
                    "type":"confirm",
                    "cls":"btn btn-info orange",
                    "callback":function(){
                        base.page.back({});
                        $(".mCSB_container").css("left",0);
                    }
                },
            ]
        });
    }
    //上一步，下一步
    var runNext = function () {
        steps.forward(function () {
            var step = steps.getStep();
            switch (step) {
                case 0:
                    return goNext(step,resourceObj.catalogUEMap.catalogUEState);
                    break;
                case 1:
                    return goNext(step,resourceObj.catalogUEMap.catalogUEState);
                    break;
                case 2:
                    return goNext(step,resourceObj.catalogUEMap.catalogUEState);
                    break;
                case 3:
                    return goNext(step,resourceObj.catalogUEMap.catalogUEState);
                    break;
            }
        })
    }
    var goNext = function (i,o) {
        if(i==o){
            base.requestTip({position:"center"}).error("下一步未编辑不可查看！");
            return false;
        }else {
            return true;
        }
    }
    var runPrev = function () {
        steps.back(function () {
            var step = steps.getStep();
            switch (step) {
                case 1:
                case 2:
                    return true;
                    break;
                default:
                    return true;
                    break;
            }
        })
    }
    var loading = function () {
        /*资源初始化*/
        base.form.init(resourceObj.catalogUEMap,$("#form1"));
        base.form.init(resourceObj.catalogUEMap,$("#form2"));
        base.form.init(resourceObj.catalogUEMap,$("#form3"));
        $.each(resourceObj.dataElmentList,function (i,o) {
            var isQuery = o.isQuery == 1?"orange":"blue";
            var isTableDisplay = o.isTableDisplay == 1?"orange":"blue";
            var html =  "<tr><td>"+o.dataName+"</td>"+
                "<td>"+o.dataEname+"</td>"+
                "<td>"+o.dataType+"</td>"+
                "<td>"+o.dataLength+"</td>"+
                "<td>"+o.dataCode+"</td>"+
                "<td>"+
                "<button rid=''  class='btn btn-link "+isQuery+" signBtn' type='role' name='' title='是否添加为查询条件' onclick='return false'><i class='fa fa-search'></i></button>" +
                "<button rid='' class='btn btn-link "+isTableDisplay+" signBtn' type='role' name='' title='是否添加为展示列表' onclick='return false'><i class='fa fa-align-justify'></i></button>" +
                "<input type='hidden' class='hiddenId' rid='" + o.dataElementId + "'>" +
                "<input type='hidden' class='hiddenDataTypeId' rid='" + o.dataTypeId + "'>"+
                "<input type='hidden' class='establishType' rid='0'>"+
                "<input type='hidden' class='dictionaryId' rid='"+o.dictionarId+"'>"
            "</td></tr>"
            $("#dataGrid tbody").append(html);
        })
        base.form.init(resourceObj.catalogUEMap,$("#form5"));
        var imgSrc=$.base+'/'+resourceObj.catalogUEMap.imagePath;
        $("#imgView").attr("src",imgSrc);
        $.each(resourceObj.dataElmentList,function (i,o) {
            var i = i+1
            var html = "<tr>"+
                "<td>"+i+"</td>"+
                "<td>"+o.dataName+"</td>"+
                "<td>"+o.dataEname+"</td>"+
                "<td>"+o.dataCode+"</td>"+
                "<td>"+o.dataType+"</td>"+
                "<td>"+o.dataLength+"</td>"+
                "</tr>"
            $("#form5 #grid tbody").append(html)
        })
    }
    return {
        main: function () {
            /*获取当前资源数据*/
            base.ajax({
                url: $.base + "/catalogingManager/queryCatalogUEAll",
                type: "post",
                async:false,
                params: {"id": $("#rowId").attr("rid")},
                success: function (d) {
                    resourceObj = d;
                }
            })
            setSteps();
            common.init();
            loading();
        }
    };
});