/**
 * Created by Administrator on 2018/3/7 0007.
 */
define(["base","app/commonApp","app/form"],function (base,common,conditions) {
    var condition = function(val,ciId){
        $.ajax({
            url:$.base+"/resciRegister/addResciRegisterInit",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"catalogueId":val}),
            success:function(result){
                //判断得到的数组的长度是否超过6个，如果超过6个就将表格变成两排，反之一排
                if(result.length>0){
                    var len = result.length;
                    len> 6 ? a(result): b(result);
                    detail(val,ciId);
                    $(".dataBoxIcon").hide();
                }
            }
        })
    };
    //获取表单的详情
    function detail(val,ciId){
        $.ajax({
            url:$.base+"/resciRegister/queryResciRegisterById",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"catalogueId":val,"id":ciId}),
            success:function(obj){
                $.each(obj,function (key,value) {
                    $("#wireTable [name='"+key+"']").prop("disabled",true).css({"background":"rgba(0,0,0,0)","cursor":"default"}).val(value)
                })
            }
        })
    }
    function a(arr){
        $("#resourceRegister .ui-form-box").width(1020);
        var content = conditions.form(arr,false);
        var html="";
        var tds="";
        var wireTable = document.getElementById("wireTable");
        //先判断content[1].input的长度是否为偶数，不为偶数的时候处理成偶数
        var inputLen = content[1].input.length;
        if( inputLen%2 !=0){
            content[0]["label"].push("");
            content[1]["input"].push("");
        }
        $.each(content[1].input,function(index,item){
            tds+="<td type='label'>"+content[0]["label"][index]+"</td><td>"+item+"</td>";
            if(index%2!=0){
                var tr = document.createElement("tr");
                wireTable.appendChild(tr);
                $(tr).html(tds);
                tds="";
            }
        })
    }
    function b(arr){
        var content = conditions.form(arr,false);
        var html="";
        var tds="";
        var wireTable = document.getElementById("wireTable");
        $.each(content[1].input,function(index,item){
            tds+="<td type='label'>"+content[0]["label"][index]+"</td><td>"+item+"</td>";
            var tr = document.createElement("tr");
            wireTable.appendChild(tr);
            $(tr).html(tds);
        })
    }
    return {
        main:function(){
            var val = $("#treeId").val();
            var ciId = $("#checkValue").val();
            common.init();
            condition(val,ciId);
        }
    }
});