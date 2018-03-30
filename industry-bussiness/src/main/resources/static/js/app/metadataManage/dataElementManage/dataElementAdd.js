define(["base","app/commonApp","app/metadataManage/dataElementManage/dataElementManage"],function(base,common,dataElementManage){

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
                        if(treeNode.level == 0){
                            return "break";
                        }
                        $("#comId").val(treeNode.id);
                        submitter(treeNode.id);
                    }
                });
                $("#comName").val("请选择");
            }
        })
    }

    //提交人
    var submitter = function(ids){
        common.ajax({
            url:$.base+"/userManager/queryByComId",
            type:"post",
            params:{"roleId":7,"comId":ids},
            success:function(d){
                $("#userId").empty();
                $.each(d,function (i,o) {
                    $("#userId").append("<option value='"+o.USER_ID+"'>"+o.USER_NAME+"</option>");
                })
            }
        })
    }

    return {
        main:function(){
            common.init();
            getUnit();//提交单位
            // 数据类型
            base.ajax({
                url:$.base+"/dictionarManager/dict",
                type:'post',
                params:{"classId":"1"},
                success:function(d){
                    $.each(d,function(i,o){
                        $("#dataType").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                    })
                }
            })
            //数据元分类
            base.ajax({
                url:$.base+"/dictionarManager/dict",
                type:'post',
                params:{"classId":"2"},
                success:function(d){
                    $.each(d,function(i,o){
                        $("#itemName").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                    })
                }
            })
            //改变数据类型改变字典属性
            $("#dataType").on("change",function () {
                var val = $(this).val();
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
            })
            // 版本号
            base.ajax({
                url:$.base+"/dictionarManager/dict",
                type:'post',
                params:{"classId":"3"},
                success:function(d){
                    $.each(d,function(i,o){
                        $("#versionName").append('<option value="'+o.id+'">'+o.itemName+'</option>')
                    })
                }
            })
            //字典属性
            var dictionaryItem = function(){
                base.ajax({
                    url:$.base+"/dictionarManager/queryDictionarSelect",
                    type:'post',
                    params:{"classId":"3"},
                    success:function(d){
                        $.each(d,function(i,o){
                            $("#dictionaryItem").append('<option value="'+o.id+'">'+o.name+'</option>')
                        })
                    }
                })
            }
            common.setSubmitForm($("#requestSubmit"),function(){
                var params = base.form.getParams($("#dataElement"));
                params.enabledState = 0;
                params.establishType = 0;
                params.dataLength = 255;
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
                           /**提交操作**/
                           common.submit({
                               url:$.base+"/dataManager/addData",
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
                       }else{
                           base.requestTip({position:"center"}).error(d.message);
                       }
                    }
                });
            });
        }
    };
});