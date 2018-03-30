define(["base", "app/commonApp", "app/metadataManage/resourceCatalog/resourceCatalog"], function (base, common, resourceCatalog) {
    var dataGrid = null;
    var gridOption1 = null;
    var steps = null;
    var resourceId = null;
    var resciCode = null;
    var sessionInfo = null;
    /**设置步骤组件**/
    var setSteps = function (type) {
        steps = base.steps({
            container: $("#ui-steps"),
            // currentStep:2,
            height: common.getStepsHeight(),
            data: [
                {"label": "填写编目申请", "contentToggle": "#content1"},
                {
                    "label": "填写元数据", "contentToggle": "#content2", "callback": function () {
                }
                },
                {
                    "label": "编辑OID标识", "contentToggle": "#content3", "callback": function () {
                }
                },
                {
                    "label": "编辑数据项", "contentToggle": "#content4", "callback": function () {
                }
                },
                {
                    "label": "编目预览", "contentToggle": "#content5", "callback": function () {
                }
                }
            ],
            buttons: [
                {
                    "type": "back", "cls": "btn btn-info blue", "icon": "ion-chevron-left", "callback": function (a) {
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
                {label: "保存","type":"confirm","cls":"btn btn-info blue","icon":"fa fa-check","callback":function(){
                    base.ajax({
                        url: $.base + "/catalogingManager/catalogingSave",
                        type: "post",
                        params: {"catalogUEState": 4, "resciState": 0, "id": resourceId},
                        success: function (d) {
                            if(d.success){
                                base.page.back({
                                    callback:function(){
                                        if(resourceCatalog){
                                            resourceCatalog.refresh();
                                        }
                                    }
                                });
                            }
                        }
                    })
                }
                },
                {label: "保存并提交","type":"confirm","cls":"btn btn-info blue","icon":"fa fa-check","callback":function(){
                    base.ajax({
                        url: $.base + "/catalogingManager/catalogingSave",
                        type: "post",
                        async:false,
                        params: {"catalogUEState": 4, "resciState": 0, "id": resourceId},
                        success: function (d) {
                        }
                    })
                    base.ajax({
                        url: $.base + "/catalogingManager/stateChangeSingle",
                        type: "post",
                        async:false,
                        params: {"resciState": 1, "id": resourceId},
                        success: function (d) {
                            base.page.back({
                                callback:function(){
                                    if(resourceCatalog){
                                        resourceCatalog.refresh();
                                    }
                                }
                            });
                        }
                    })
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
                    var pass = base.form.validate({
                        form: $("#form1"),
                        checkAll: true,
                        passCallback: function () {
                        }
                    });
                    if(!pass){
                        return false;
                    }
                    var params = base.form.getParams($("#form1"));
                    params.catalogId = $("#treeId").val();
                    params.catalogUEState = 0;
                    common.addLoadBg();
                    base.ajax({
                        url: $.base + "/catalogingManager/catalogingApplication",
                        type: "post",
                        params: params,
                        success: function (d) {
                            resourceId = d.id;
                            common.overLoadBg();
                        }
                    })
                    return pass;
                    break;
                case 1:
                    var pass = base.form.validate({
                        form: $("#form2"),
                        checkAll: true,
                        passCallback: function () {
                        }
                    });
                    if($("#form2 #imageUp .txt").html()==""){
                        pass = false;
                        $("#form2 #imageUp").append('<div class="ui-form-error">资源图片必选</div>')
                    }
                    if(resciCode!=$("#form2 input[name='resciCode']").val()){
                        base.ajax({
                            url: $.base + "/catalogingManager/resciCodeCheck",
                            type: "post",
                            async:false,
                            params: {"resciCode":$("#form2 input[name='resciCode']").val()},
                            success: function (d) {
                                if(!d.success){
                                    pass = false;
                                    $("#form2 input[name='resciCode']").addClass("errorStyle");
                                    $("#form2 input[name='resciCode']").parent().append("<div class='ui-form-error'>资源编码重复</div>");
                                }
                            }
                        })
                    }
                    if(!pass){
                        return false;
                    }
                    var params = base.form.getParams($("#form2"));
                    delete params["file"];
                    params.id = resourceId;
                    params.catalogUEState = 1;
                    common.addLoadBg();
                    base.ajax({
                        url: $.base + "/catalogingManager/catalogingApplication",
                        type: "post",
                        params: params,
                        success: function (d) {
                            resciCode = d.resciCode;
                            common.overLoadBg();
                        }
                    })
                    return pass;
                    break;
                case 2:
                    var pass = base.form.validate({
                        form: $("#form3"),
                        checkAll: true,
                        passCallback: function () {
                        }
                    });
                    if(!pass){
                        return false;
                    }
                    var params = {}
                    params.oid = $("#form3 [name='oid']").val();
                    params.id = resourceId;
                    params.catalogUEState = 2;
                    pass = false;
                    base.ajax({
                        url: $.base + "/catalogingManager/oidCheck",
                        type: "post",
                        params: {"resciCode": resciCode, "oid": params.oid},
                        async: false,
                        success: function (d) {
                            if (d.success) {
                                common.addLoadBg()
                                base.ajax({
                                    url: $.base + "/catalogingManager/catalogingSaveStep3",
                                    type: "post",
                                    params: params,
                                    success: function (d) {
                                        common.overLoadBg();
                                    }
                                })
                                pass = true;
                            } else {
                                base.requestTip({position: "center"}).error(d.message);
                            }
                        }
                    })
                    $("#dataGrid tbody tr").each(function (i,o) {
                        $(this).find(".signBtn:first").removeClass(".blue").addClass("orange").attr("title","去除查询条件");
                        if(i>=5){
                            return false;
                        }
                    })
                    $("#dataGrid tbody tr").each(function (i,o) {
                        $(this).find(".signBtn:last").removeClass(".blue").addClass("orange").attr("title","").attr("title","去除列表展示项");
                        if(i>=7){
                            return false;
                        }
                    })
                    return pass;
                    break;
                case 3:
                    var params = {
                        DataElementList: []
                    };
                    var isQ = false;
                    var isT = false;
                    params.catalogueId = resourceId;
                    $("#dataGrid tbody tr").each(function (i, o) {
                        params.DataElementList.push({
                            dataName: $(o).find("td").eq(0).html(),
                            dataEname: $(o).find("td").eq(1).html(),
                            dataTypeId: $(o).find(".hiddenDataTypeId").attr("rid"),
                            dataLength: $(o).find("td").eq(3).html(),
                            dataCode: $(o).find("td").eq(4).html(),
                            id: $(o).find(".hiddenId").attr("rid"),
                            isQuery: $(o).find(".signBtn:first").hasClass("orange")?"1":"0", //是否展示查询条件 0否1是
                            isTbaleDisplay: $(o).find(".signBtn:last").hasClass("orange")?"1":"0",//是否展示在列表页 0否1是
                            establishType: $(o).find(".establishType").attr("rid"),//0:数据元关联 1：临时创建
                            constraint:$(o).find(".constraint").attr("rid"),
                            dictionaryId:$(o).find(".dictionaryId").attr("rid")?$(o).find(".dictionaryId").attr("rid"):null, //字典属性
                            index:i+1
                        })
                        if($(o).find(".signBtn:first").hasClass("orange")){
                            isQ = true;
                        }
                        if($(o).find(".signBtn:last").hasClass("orange")){
                            isT = true;
                        }
                    })
                    if(!isQ){
                        base.requestTip({position: "center"}).error("查询条件不能为空！");
                    }
                    if(!isT){
                        base.requestTip({position: "center"}).error("列表展示不能为空！");
                    }
                    common.addLoadBg();
                    base.ajax({
                        url: $.base + "/catalogingManager/catalogingSaveStep4",
                        type: "post",
                        params: params,
                        success: function (d) {
                            base.form.init(d.catalogUEMap,$("#form5"))
                            $("#form5 img").attr("src",$.base+"/"+d.catalogUEMap.imagePath);
                            $("#form5 #grid tbody").empty();
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
                                $("#form5 #grid tbody").append(html)
                            })
                            common.overLoadBg();
                        }
                    })
                    return true;
                    break;
            }
        })
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
    /*设置表格*/
    var drawGrid = function (treeId) {
        if (dataGrid) {
            dataGrid.destroy();
        }
        dataGrid = common.drawGrid({
            container: $("#dataGrid"),
            gridOption: gridOption1
        });
    };
    var setGridOption = function () {
        gridOption1 = common.getGridOption();
        gridOption1.ajax = {
            url: $.base + "/catalogingManager/queryDataElementM",
            type: "post",
            "contentType": "application/json",
            data: function (d) {
                var d = {
                    pageNo: 1,
                    pageSize: 0,
                };
                return JSON.stringify(d);
            }
        };
        gridOption1.paging = false;
        gridOption1.info = false;
        gridOption1.columns = [
            {"data": "dataName", "sWidth": "14%"},
            {"data": "dataEname", "sWidth": "16%"},
            {"data": "dataType", "sWidth": "15%"},
            {"data": "dataLength", "sWidth": "14%"},
            {"data": "dataCode", "sWidth": "15%"},
            {"data": "id", "sWidth": "26%"}
        ];
        gridOption1.columnDefs = [
            {
                "render": function (rowNumber, type, rowData, row) {
                    return "<button rid=''  class='btn btn-link blue shiftUp' type='role' name='上移' title='上移' onclick='return false'><i class='fa fa-arrow-up'></i></button>" +
                        "<button rid='' class='btn btn-link blue shiftDown' type='role' name='下移' title='下移' onclick='return false'><i class='fa fa-arrow-down'></i></button>" +
                        "<button rid=''  class='btn btn-link blue signBtn' type='role' name='' title='添加为查询条件' onclick='return false'><i class='fa fa-search'></i></button>" +
                        "<button rid='' class='btn btn-link blue signBtn' type='role' name='' title='添加为列表展示项' onclick='return false'><i class='fa fa-align-justify'></i></button>" +
                        "<input type='hidden' class='hiddenId' rid='" + rowData.id + "'>" +
                        "<input type='hidden' class='hiddenDataTypeId' rid='" + rowData.dataTypeId + "'>"+
                        "<input type='hidden' class='establishType' rid='0'>"+
                        "<input type='hidden' class='dictionaryId' rid='"+rowData.dictionarId+"'>"+
                        "<input type='hidden' class='constraint' rid='"+rowData.constraint+"'>"
                },
                "targets": 5
            }
        ];
        gridOption1.drawCallback = function (setting) {
        }
    };
    var loading = function () {
        // 新增数据项模态框
        $("#addDataItem").on("click", function () {
            var modal = base.modal({
                width: 700,
                height: 400,
                label: "新增数据项",
                url: $.base+"/catalogingManager/dataItem",
                drag: true,
                buttons: [
                    {
                        label: "确定",
                        cls: "btn btn-info",
                        clickEvent: function () {
                            base.form.validate({
                                form: $("#form"),
                                checkAll: true,
                                passCallback: function () {
                                    var params = base.form.getParams($("#form"));
                                    var dataLength = params.dataLength?params.dataLength:"";
                                    var html =  "<tr><td>"+params.dataName+"</td>"+
                                                "<td>"+params.dataEname+"</td>"+
                                                "<td>"+$("#dataTypeSelect").find("option:selected").text()+"</td>"+
                                                "<td>"+dataLength+"</td>"+
                                                "<td></td>"+
                                                "<td>"+
                                                    "<button rid=''  class='btn btn-link blue shiftUp' type='role' name='上移' title='上移' onclick='return false'><i class='fa fa-arrow-up'></i></button>" +
                                                    "<button rid='' class='btn btn-link blue shiftDown' type='role' name='下移' title='删除' onclick='return false'><i class='fa fa-arrow-down'></i></button>" +
                                                    "<button rid=''  class='btn btn-link blue modifyBtn' type='role' name='' title='修改' onclick='return false'><i class='fa fa-pencil'></i></button>" +
                                                    "<button rid='' class='btn btn-link blue deleteBtn' type='role' name='删除' title='删除' onclick='return false'><i class='fa fa-trash-o'></i></button>" +
                                                    "<button rid=''  class='btn btn-link blue signBtn' type='role' name='' title='添加为查询条件' onclick='return false'><i class='fa fa-search'></i></button>" +
                                                    "<button rid='' class='btn btn-link blue signBtn' type='role' name='' title='添加为列表展示项' onclick='return false'><i class='fa fa-align-justify'></i></button>" +
                                                    "<input type='hidden' class='hiddenId' rid='0'>" +
                                                    "<input type='hidden' class='hiddenDataTypeId' rid='" + params.dataTypeId + "'>"+
                                                    "<input type='hidden' class='establishType' rid='1'>"+
                                                    "<input type='hidden' class='dictionaryId' rid='"+params.dictionaryId+"'>"+
                                                    "<input type='hidden' class='hiddenObj' rid='"+JSON.stringify(params)+"'>"
                                                "</td></tr>"
                                    $("#dataGrid tbody").append(html);
                                    modal.hide();
                                }
                            });
                        }
                    },
                    {
                        label: "取消",
                        cls: "btn btn-warning",
                        clickEvent: function () {
                            modal.hide();
                        }
                    }
                ]
            });
        })
        // 关联模态框
        $("#relevanceBtn").on("click", function () {
            var modal = base.modal({
                width: 950,
                height: 500,
                label: "关联数据项",
                url: $.base+"/catalogingManager/dataElementRelevance",
                drag: true,
                buttons: [
                    {
                        label: "确定",
                        cls: "btn btn-info",
                        clickEvent: function () {
                            $("#gridRelevance tbody input[type='checkbox']").each(function () {
                                if($(this).prop("checked")){
                                    var obj = JSON.parse($(this).parents("tr").find(".obj").val())
                                    var html =  "<tr><td>"+obj.dataName+"</td>"+
                                        "<td>"+obj.dataEname+"</td>"+
                                        "<td>"+obj.dataType+"</td>"+
                                        "<td>"+obj.dataLength+"</td>"+
                                        "<td>"+obj.dataCode+"</td>"+
                                        "<td>"+
                                        "<button rid=''  class='btn btn-link blue shiftUp' type='role' name='上移' title='上移' onclick='return false'><i class='fa fa-arrow-up'></i></button>" +
                                        "<button rid='' class='btn btn-link blue shiftDown' type='role' name='下移' title='下移' onclick='return false'><i class='fa fa-arrow-down'></i></button>" +
                                        "<button rid='' class='btn btn-link blue deleteBtn' type='role' name='删除' title='删除' onclick='return false'><i class='fa fa-trash-o'></i></button>" +
                                        "<button rid=''  class='btn btn-link blue signBtn' type='role' name='' title='添加为查询条件' onclick='return false'><i class='fa fa-search'></i></button>" +
                                        "<button rid='' class='btn btn-link blue signBtn' type='role' name='' title='添加为列表展示项' onclick='return false'><i class='fa fa-align-justify'></i></button>" +
                                        "<input type='hidden' class='hiddenId' rid='"+obj.id+"'>" +
                                        "<input type='hidden' class='hiddenDataTypeId' rid='" + obj.dataTypeId + "'>"+
                                        "<input type='hidden' class='establishType' rid='0'>"+
                                        "<input type='hidden' class='dictionaryId' rid='"+obj.dictionarId+"'>"+
                                        "<input type='hidden' class='constraint' rid='"+obj.constraint+"'>"
                                        "</td></tr>"
                                    $("#dataGrid tbody").append(html);
                                }
                            })
                            modal.hide();
                        }
                    },
                    {
                        label: "取消",
                        cls: "btn btn-warning",
                        clickEvent: function () {
                            modal.hide();
                        }
                    }
                ]
            });
        })
        /*表格操作*/
        $("#dataGrid").on("click",".signBtn",function () {
            if($(this).hasClass("blue")){
                $(this).removeClass("blue").addClass("orange");
                if($(this).find("i").hasClass("fa-search")){
                    $(this).attr("title","去除查询条件");
                }else {
                    $(this).attr("title","去除列表展示项");
                }
            }else {
                $(this).removeClass("orange").addClass("blue");
                if($(this).find("i").hasClass("fa-search")){
                    $(this).attr("title","添加为查询条件");
                }else {
                    $(this).attr("title","添加列表展示项");
                }
            }
        })
        $("#dataGrid").on("click",".deleteBtn",function () {
            var $this = this;
            base.confirm({
                label:"提示",
                text:"<div style='text-align:center;font-size:13px;'>确定删除吗？</div>",
                confirmCallback:function(){
                    $($this).parents("tr").remove();
                }
            });
        })
        // 修改新增数据
        $("#dataGrid").on("click",".modifyBtn",function () {
            var $this = this;
            var modal = base.modal({
                width: 700,
                height: 400,
                label: "新增数据项",
                url: $.base+"/catalogingManager/dataItem",
                drag: true,
                buttons: [
                    {
                        label: "确定",
                        cls: "btn btn-info",
                        clickEvent: function () {
                            base.form.validate({
                                form: $("#form"),
                                checkAll: true,
                                passCallback: function () {
                                    var params = base.form.getParams($("#form"));
                                    var dataLength = params.dataLength?params.dataLength:"";
                                    var html =  "<td>"+params.dataName+"</td>"+
                                        "<td>"+params.dataEname+"</td>"+
                                        "<td>"+$("#dataTypeSelect").find("option:selected").text()+"</td>"+
                                        "<td>"+dataLength+"</td>"+
                                        "<td></td>"+
                                        "<td>"+
                                        "<button rid=''  class='btn btn-link blue shiftUp' type='role' name='上移' title='上移' onclick='return false'><i class='fa fa-arrow-up'></i></button>" +
                                        "<button rid='' class='btn btn-link blue shiftDown' type='role' name='下移' title='删除' onclick='return false'><i class='fa fa-arrow-down'></i></button>" +
                                        "<button rid=''  class='btn btn-link blue modifyBtn' type='role' name='' title='修改' onclick='return false'><i class='fa fa-pencil'></i></button>" +
                                        "<button rid='' class='btn btn-link blue deleteBtn' type='role' name='删除' title='删除' onclick='return false'><i class='fa fa-trash-o'></i></button>" +
                                        "<button rid=''  class='btn btn-link blue signBtn' type='role' name='' title='添加为查询条件' onclick='return false'><i class='fa fa-search'></i></button>" +
                                        "<button rid='' class='btn btn-link blue signBtn' type='role' name='' title='添加为列表展示项' onclick='return false'><i class='fa fa-align-justify'></i></button>" +
                                        "<input type='hidden' class='hiddenId' rid='0'>" +
                                        "<input type='hidden' class='hiddenDataTypeId' rid='" + params.dataTypeId + "'>"+
                                        "<input type='hidden' class='establishType' rid='1'>"+
                                        "<input type='hidden' class='dictionaryId' rid='"+params.dictionaryId+"'>"+
                                        "<input type='hidden' class='hiddenObj' rid='"+JSON.stringify(params)+"'>"
                                    "</td>"
                                    $($this).parents("tr").html(html);
                                    modal.hide();
                                }
                            });
                        }
                    },
                    {
                        label: "取消",
                        cls: "btn btn-warning",
                        clickEvent: function () {
                            modal.hide();
                        }
                    }
                ],
                callback:function () {
                    var params = JSON.parse($($this).parents("tr").find(".hiddenObj").attr("rid"));
                    base.form.init(params,$("#form"))
                    $("#hidemodifyId").val(params.dictionaryId);
                }
            });
        })
        $("#dataGrid").on("click",".shiftUp",function () {
            var index = $(this).parents('tr').index()+1;
            var indexUp = index-1;
            $("#dataGrid tbody tr:nth-child(" + index + ")").insertBefore($("#dataGrid tbody tr:nth-child("+indexUp+")"));
        })
        $("#dataGrid").on("click",".shiftDown",function () {
            var index = $(this).parents('tr').index()+1;
            var indexDown = index+1;
            $("#dataGrid tbody tr:nth-child(" + index + ")").insertAfter($("#dataGrid tbody tr:nth-child("+indexDown+")"));
        })
        //联系人
        base.ajax({
            url: $.base + "/userManager/sessionInfo",
            type: "post",
            params: {"roleId": "7"},
            success: function (d) {
                sessionInfo = d;
                $.each(d, function (i, o) {
                    $("#form1 .link-user").append("<option value='" + o.userId + "'>" + o.userName + "</option>")
                    if(i == 0){
                        $("#form1 input[name='telephone']").val(o.telePhone);
                    }
                })
            }
        })
        $("#form1 .link-user").on("change",function () {
            var _this = this;
            $.each(sessionInfo,function (i,o) {
                if($(_this).val() == o.userId){
                    $("#form1 input[name='telephone']").val(o.telePhone);
                }
            })
        })
        // 主题分类
        base.ajax({
            url: $.base + "/dictionarManager/dict",
            params: {"classId": "5"},
            type: "post",
            success: function (d) {
                $.each(d, function (i, o) {
                    $("#form2 [name='topicId']").append("<option value='" + o.id + "'>" + o.itemName + "</option>")
                })
            }
        })
        // 行业分类
        base.ajax({
            url: $.base + "/dictionarManager/dict",
            type: "post",
            params: {"classId": "4"},
            success: function (d) {
                $.each(d, function (i, o) {
                    $("#form2 [name='industryId']").append("<option value='" + o.id + "'>" + o.itemName + "</option>")
                })
            }
        })
        // 获取oid
        $("#getOid").on("click", function () {
            base.ajax({
                url: $.base + "/catalogingManager/getoid",
                type: "post",
                    params: {"resciCode": resciCode},
                success: function (d) {
                    if (d.oid) {
                        $("#form3 [name='oid']").val(d.oid);
                    } else {
                        base.requestTip({position: "center"}).error("资源编码错误");
                    }
                }
            })
        })
        //资源图片上传
        $("#imageUp input[type='button']").on("click",function(){
            $("#form2 #file").remove();
            $("#form2").append("<input type='file' id='file' name='file' accept = '.png,.jpg,.gif,image/png,image/jpg,image/gig' style='display: none'>")
            $("#file").trigger("click");
        })
        $("#form2").on("change","#file",function(){
            var size = base.form.validateFileSize("#file",10*1024);
            $("#form2 #imageUp .ui-form-error").remove();
            var fileType = getFileType($("#file").val());
            //判断上传的附件是否为图片
            if("jpg"!=fileType && "png"!=fileType && "gif"!=fileType){
                $("#file").val("");
                $("#form2 #imageUp").append('<div class="ui-form-error">请上传JPG,PNG,GIF格式的图片</div>');
                return false;
            }
            if(!size){
                $("#form2 #imageUp").append('<div class="ui-form-error">图片大小不可超过10M</div>')
                return false;
            }
            base.form.fileUpload({
                url:$.base+"/catalogingManager/uploadPic",
                id:"file",
                success:function(d){
                    $("#imageUp .txt").html(d.image);
                    $("#form2 .image").val(d.image);
                    $("#form2 .imagePath").val(d.imagePath);
                }
            });
        })
    }
    function getFileType(filePath){
        var startIndex = filePath.lastIndexOf(".");
        if(startIndex != -1)
            return filePath.substring(startIndex+1, filePath.length).toLowerCase();
        else return "";
    }
    return {
        main: function () {
            setSteps();
            common.init();
            loading();
            setGridOption();
            drawGrid();
        }
    };
});