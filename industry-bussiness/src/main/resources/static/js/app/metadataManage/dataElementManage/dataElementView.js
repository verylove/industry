define(["base","app/commonApp"],function(base,common){
    return {
        main:function(){
            common.init();
            var changeDictionaryItem = function(val){
                var html = '<td type="label">字典属性</td>'+
                    '<td name="dictionarName">'+
                    '</td>'
                if(val == 3){
                    $("#dictionaryCentent").append(html)
                }
            }
            base.ajax({
                url:$.base+"/dataManager/queryDataById",
                params:{"id":$("#rowId").attr("rid")},
                type:'post',
                success:function(d){
                    changeDictionaryItem(d.dataTypeId);
                    base.form.init(d,$("#dataElement"));
                    if(d.constraint=="M"){
                        $("td[name='constraint']").text("必填");
                    }else if(d.constraint=="O"){
                        $("td[name='constraint']").text("非必填");
                    }
                }
            })
        }
    };
});