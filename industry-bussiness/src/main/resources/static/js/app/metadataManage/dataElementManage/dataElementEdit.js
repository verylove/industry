define(["base","app/commonApp","app/metadataManage/dataElementManage/dataElementManage"],function(base,common,dataElementManage){
    //获得初始化数据
    var oldDataName=null;
    var oldVersionId=null;
    var getInitData=function(){
        base.ajax({
            url:$.base+"/dataManager/queryDataById",
            params:{"id":$("#rowId").attr("rid")},
            type:'post',
            success:function(d){
                changeDictionaryItem(d.dataTypeId);
                base.form.init(d,$("#dataElement"));
                submitter(d.comId,d.userId);
                oldDataName=d.dataName;
                oldVersionId=d.versionId;
            }
        })
    }
    var changeDictionaryItem = function(val){
        var html = '<td type="label" class="dictionaryItem">字典属性</td>'+
            '<td class="dictionaryItem">'+
            '<select name="dictionaryId" class="form-control" id="dictionaryItem">'+
            '</select>'+
            '</td>'
        if(val == 3){
            $("#dictionaryCentent").append(html)
            dictionaryItem()
        }else{
            $("#dictionaryCentent .dictionaryItem").remove();
        }
    }
    //字典属性
    var dictionaryItem = function(){
        base.ajax({
            url:$.base+"/dictionarManager/queryDictionarSelect",
            type:'post',
            async:false,
            params:{"classId":"3"},
            success:function(d){
                $.each(d,function(i,o){
                    $("#dictionaryItem").append('<option value="'+o.id+'">'+o.name+'</option>')
                })
            }
        })
    }

    /*提交单位*/
    var getUnit = function(){
        common.ajax({
            url:$.base+"/organizationManager/listOrganizationTree",
            type:"post",
            success:function(d){
                var treeSelectObj = base.form.treeSelect({
                    container:$("#comName"),
                    data:d,
                    clickCallback:function(event,treeId,treeNode){
                        if(treeNode.isParent){
                            return "break";
                        }
                        $("#comId").val(treeNode.id);
                        submitter(treeNode.id,treeNode.userId);
                    }
                });
            }
        })
    }

    //提交人
    var submitter = function(ids,userId){
        common.ajax({
            url:$.base+"/userManager/queryByComId",
            type:"post",
            params:{"roleId":7,"comId":ids},
            success:function(d){
                $("#userId").empty();
                $.each(d,function (i,o) {
                    $("#userId").append("<option value='"+o.USER_ID+"'>"+o.USER_NAME+"</option>");
                })
                $("#userId").find("option[value='"+userId+"']").attr("selected",true);
            }
        })
    }
    // 数据类型
    var getDataType=function(){
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:'post',
            async:false,
            params:{"classId":"1"},
            success:function(d){
                $.each(d,function(i,o){
                    $("#dataType").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                })
            }
        })
    }
    //数据元分类
    var getDataElement=function(){
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:'post',
            async:false,
            params:{"classId":"2"},
            success:function(d){
                $.each(d,function(i,o){
                    $("#itemName").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                })
            }
        })
    }
    //版本号
    var getVersion=function(){
        base.ajax({
            url:$.base+"/dictionarManager/dict",
            type:'post',
            async:false,
            params:{"classId":"3"},
            success:function(d){
                $.each(d,function(i,o){
                    $("#versionName").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                })
            }
        })
    }
    //改变数据类型改变字典属性
    var changeDataType=function(){
        $("#dataType").on("change",function () {
            var val = $(this).val();
            changeDictionaryItem(val)
        })
    }

    return {
        main:function(){
            common.init();
            getInitData();//获得初始化数据
            getUnit();//提交单位
            getDataType();// 数据类型
            getDataElement();//数据元分类
            getVersion();// 版本号
            changeDataType();//改变数据类型改变字典属性

            var setSubmit=function(params){
                /**提交操作**/
                common.submit({
                    url:$.base+"/dataManager/updateData",
                    params:params,
                    type:"post",
                    callback:function(){
                        /**提交成功后返回父页面并刷新列表**/
                        base.page.back({
                            callback:function(){
                                if(dataElementManage){
                                    dataElementManage.refresh();
                                }
                            }
                        });
                    }
                });
            }

            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#dataElement"));
                params.enabledState = 0;
                params.establishType = 0;
                params.dataLength = 255;
                params.id = $("#rowId").attr("rid");
                if(oldDataName==params.dataName&&oldVersionId==params.versionId){
                    setSubmit(params);
                }else{
                    //校验数据元名称和版本号
                    var checkObj={
                        "dataName":params.dataName,
                        "versionId":params.versionId
                    }
                    common.ajax({
                        url:$.base+"/dataManager/addDataCheck",
                        type:"post",
                        params:checkObj,
                        success:function(d){
                            if(d.success){
                                setSubmit(params);
                            }else{
                                base.requestTip({position:"center"}).error(d.message);
                            }
                        }
                    });
                }
            });
        }
    };
});