define(["base","app/commonApp","app/metadataManage/examineCatalog/examineCatalog"],function(base,common,examineCatalog){
    var loading = function(){
        base.ajax({
            url:$.base+"/catalogingManager/review",
            params:{"cataUEId":$("#rowId").attr("rid"),examineStep:0},
            type:"post",
            success:function (d) {
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
            common.init();
            loading();
        }
    };
});