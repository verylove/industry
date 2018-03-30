define(["base","app/commonApp"],function(base,common){
    var loading = function () {
        $("#dataTypeSelect").on("change",function () {
            if($(this).val() == 3){
                var html = '<label for="dictionaryId" style="margin-left:140px;">字典属性</label>'+
                    '<div class="ui-form-inputGroup">'+
                    '<select class="form-control" name="dictionaryId" id="dictionaryId"></select>'+
                    '</div>'
                $("#dictionaryItem").append(html)
                base.ajax({
                    url:$.base+"/dictionarManager/queryDictionarSelect",
                    type:"post",
                    async:false,
                    success:function (d) {
                        $.each(d,function (i,o) {
                            $("#dictionaryId").append("<option value='"+o.id+"'>"+o.name+"</option>")
                        })
                    }
                })
                $("#dataLengthInput").empty();
            }else if($(this).val()==1) {
                var html = '<label for="dataLength" style="margin-left:140px;">数据项长度</label>'+
                    '<div class="ui-form-inputGroup">'+
                    '<input type="text" name="dataLength" class="form-control gray" value="50" disabled/>'+
                    '</div>'+
                    '<span class="required"></span>';
                $("#dataLengthInput").append(html);
                $("#dictionaryItem").empty();
            }else {
                $("#dataLengthInput").empty();
                $("#dictionaryItem").empty();
            }
        })
        $("#dataTypeSelect").trigger("change");
        $("#dictionaryId").val($("#hidemodifyId").val());
    }
    return {
        main:function(){
            loading();
        }
    };
});
