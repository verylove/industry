/**
 * Created by Administrator on 2018/3/5 0005.
 */
define(["base","date5.0"],function(base,laydate){
    var self = {  //flag为false的时候表示新建和修改
        form:function(arr,flag){ //arr即为动态表单的数组
            var html;
            var num=0;//先假设字的个数是0个
            flag ? html="" : html=[{"label":[]},{"input":[]}] ;
            $.each(arr,function (index,item) {
                item.attributeName.length>num ? num=item.attributeName.length : num=num;
            });
            $.each(arr,function (index,item) {
                var label = "";
                item.required==1   ?  label="<label class='addRequired' style='width:"+num*16+"px;text-align:right;padding-right:10px'>"+item.attributeName+"</label>":
                    label="<label style='width:"+num*16+"px;text-align:right;padding-right:10px' for='"+item.columnName+"'>"+item.attributeName+"</label>";
                var input="";
                switch (item.widgetValue){
                    case "Input": //输入框 input
                        item.required==1 ?  input="<input type='text' WidgetValue='"+item.widgetValue+"' name='"+item.columnName+"' class='form-control' placeholder='' role='{required:true,length:50}'>":
                            input="<input type='text' WidgetValue='"+item.widgetValue+"' name='"+item.columnName+"' class='form-control' placeholder='' role='{length:50}'>";
                        break;
                    case "Select"://下拉框 Select
                        if(item.selectUrl){
                            item.required==1? input="<select class='form-control' WidgetValue='"+item.widgetValue+"'name='"+item.columnName+"'role='{required:true}'>"+getSelect(item.selectUrl,item.dictionaryId)+"</select>":
                                input="<select class='form-control' WidgetValue='"+item.widgetValue+"'name='"+item.columnName+"'>"+getSelect(item.selectUrl,item.dictionaryId)+"</select>"
                        }else{
                            input="<select class='form-control' WidgetValue='"+item.widgetValue+"' name='"+item.columnName+"'itemName='"+item.itemName+"'></select>";
                        }
                        break;
                    case "DataBox"://日期 Datebox
                        if(flag){
                            input="<input type='text' readonly class='layui-input form-control dataWidget' name='"+item.columnName+"' id='search"+item.id+index+"'>"
                                +"<span class='fa fa-table dataBoxIcon'></span>";
                        }else{
                            item.required==1 ? input="<input type='text' readonly class='layui-input form-control' name='"+item.columnName+"' id='date"+item.id+index+"'  placeholder='' WidgetValue='"+item.widgetValue+"' role='{required:true,length:50}'>"
                                +"<span class='fa fa-table dataBoxIcon'></span>":input="<input type='text' readonly  class='layui-input form-control' name='"+item.columnName+"' id='date"+item.id+index+"'  placeholder='' WidgetValue='"+item.widgetValue+"'>"
                                +"<span class='fa fa-table dataBoxIcon'></span>";
                        }
                        break;
                }
                if(flag){
                    html += "<div class='form-group' style='position: relative'>"+label+input+"</div>";
                }else{
                    html[0]["label"].push(label);
                    html[1]["input"].push(input);
                }

            })
            return html;
        },
        initDate:function () {
            $("[WidgetValue='DataBox']").each(function(index,item){
                var _this=$(this);
                laydate.render({
                    elem: '#'+_this.attr("id"),
                    type: 'date',
                    range:true
                });
            });
            $(".dataWidget").each(function(index,item){
                var _this=$(this);
                laydate.render({
                    elem: '#'+_this.attr("id"),
                    type: 'date',
                    range:true
                });
            });
        },
        initDate1:function () {
            $("[WidgetValue='DataBox']").each(function(index,item){
                var _this=$(this);
                laydate.render({
                    elem: '#'+_this.attr("id"),
                    type: 'date'
                });
            });
            $(".dataWidget").each(function(index,item){
                var _this=$(this);
                laydate.render({
                    elem: '#'+_this.attr("id"),
                    type: 'date'
                });
            });
        }
    };
    return self;
    //获取select框
    function getSelect(uri,id){
        var options="";
        $.ajax({
            url:$.base+uri,
            type:'post',
            async:false,
            contentType:"application/json",
            data:JSON.stringify({"classId":id}),
            success:function (result) {
                if(result && result.length>0){
                    options="<option value=''>"+"请选择"+"</option>"
                    $.each(result,function (index,item) {
                        options+="<option value='"+item.id+"'>"+item.itemName+"</option>"
                    });
                }
            }
        })
        return options;
    }
});

 