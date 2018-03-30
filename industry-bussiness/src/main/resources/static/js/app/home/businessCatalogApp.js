define(["base","app/commonApp"],function(base,common){
	//定义一个颜色数组
	var colors = [ "#f9a710","#eb1065","#0064af","#de5c11","#30b231","#ee9800","#c6029f","#0e9883","#c72136",
    "#da5022","#9328b3","#00a2c4","#fe9b01","#c924af","#194dc5","#c702a2","#22a327","#533ec0","#2aa300","#f9a710",
    "#1b8cbd","#eb1065","9935ce"];
	/**设置蜂窝效果**/
	var setBeehive= function(){
		base.ajax({
			url:$.base+"/index/classify",
			type:"get",
			success:function(data){
				if(data.length>0){
					base.beehive({
						container:$(".ui-beehive-container"),
						data:data,
                        colors:colors
					});
                    getIndustryDetail();
                    clickIndustry();
				}
			}
		})
	};
	function getIndustryDetail(){
        $.ajax({
            url:$.base+"/index/queryResci",
            type:"post",
            contentType:"application/json",
            success:function(data){
                $(".ui-beehive-item").hover(
                    function (e) { //划入
                        var e = e||window.event;
                        e.preventDefault();
                        var _this = $(this);
                        var val = $(this).find("a").attr("val");
                        var offset = $(this).find("a").offset();
                        var w = $(this).find("a").width();
                        var h = $(this).find("a").height();
                        if(data[val] && data[val].length>0){
                            var dataArr=[];
                            if(data[val].length>5){
                                dataArr=data[val].slice(0,5);
                            }else{
                                dataArr=data[val];
                            }
                            var lis="";
                            $.each(dataArr,function(index,item){
                                lis += "<li>"+item.resciName+"</li>";
                            });
                            $(".hover-box").html("<div class='circles'><div></div></div>"+lis).show().css({"left":offset.left-170+2*w/3,"top":offset.top+h/3});
                        }
                    },
                    function () {//划出
                        $(".hover-box").hide().html("");
                })
            }
        })


     }
    function clickIndustry(){
        $(".ui-beehive-item-in").off().on("click",function () {
            var that = $(this).find(".ui-beehive-item-in");
            common.load($.base+"/resciPublish/resciPublishView");
            localStorage.setItem("val", $(this).attr("val"));
            localStorage.setItem("type", "industry");
        })
    }
    // var hoverBeehive = function(){
    //     $(".ui-container").append("<ul class='hover-box' style='display:none'></ul>");
    //     $(".ui-beehive-item").mouseenter(
    //         function(){
    //             var _this = $(this);
    //             var that = $(this).find(".ui-beehive-item-in");
    //             var lis="";
    //             var ct = event || window.event;
    //             //获取鼠标的位置
    //             // $.ajax({
    //             //     url:$.base+"/index/queryResciById",
    //             //     type:"post",
    //             //     contentType:"application/json",
    //             //     //data:JSON.stringify({"id":that.attr("val")}),
    //             //     success:function(data){
    //             //         if(data && data.length>0){
    //             //             $.each(data,function(index,item){
    //             //                  lis += "<li>"+item.resciName+"</li>"
    //             //             });
    //             //             var offset = that.offset();
    //             //             //左上角的同心圆
    //             //             var circles = "<div class='circles'><div></div>" +
    //             //                 "</div>";
    //             //             $(".hover-box").append(circles+lis);
    //             //             $(".hover-box").show();
    //             //             $(".hover-box").css({"left":offset.left+80,"top":offset.top+80})
    //             //
    //             //         }
    //             //
    //             //     }
    //             // })
    //         }//划入
    //     ).mouseleave(function(){
    //         $(".hover-box").empty().hide(); //划出
    //     })
    // }
	return {
		main:function(){
			setBeehive();
		}
	};
});