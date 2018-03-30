define(["base","app/commonApp","fileinput","fileinputZh"],function(base,common){
	/**�������˵�**/
	var menuArrayData = null;
	var menuMapData = null;

	var rootId = "-1";//
	var indexId = "1";//��ҳ�Ĳ˵�id
	var isequipmentInit = false;//�豸��ʼ����falseΪ��
	var equId = "77cdc80cd58711e79e0465a718803ca9";
	/**������ҳ**/
	var loadIndex = function(){
		$("#menubar .panel-title").removeClass("active");
		$("#menubar .panel-title[mid='"+indexId+"']").addClass("active");
		loadPage(indexId);
	};
	/**���ò˵���**/
	var setMenu = function(menuId){
		var showHelper =  function(v1, v2, options) {
			if(v1==false) {
				return "none";
			}else{
				return "block";
			}
		};
		common.ajax({
			//url:$.path+"/api/vpn/sysMenu/findMenus?userId="+userId,
			url:$.base+"/index/queryMenu",
			type:"get",
            dataType: "json",
			xhrFields: {withCredentials: true},
			success:function(data,status,request){
				var d = data;
				if(common.isEn){
					common.mergeMenuData(d);
				}
				menuMapData = base.arrayToMap(d,rootId,false);
				scopeMenuData = menuMapData;
				menuArrayData = base.mapToArray(menuMapData,rootId);
				
				
				if(menuId){
					$.each(menuArrayData,function(index,item){
						if(item && item.items){	
							$.each(item.items,function(i,o){
								if(menuId == o.id){
									item.active=true;
									item.items[i].active=true;
								}
							})
						}
					})
				}
				base.template({
					container:$(".ui-menu"),
					templateId:common.isEn==true?"menu-tpl-en":"menu-tpl",
					data:menuArrayData,
					helper:[{"name":"show","event":showHelper}],
					callback:function(){
						base.scroll({
							container:$(".ui-menubar")
						});
						$('#menubar .panel-collapse').on('hide.bs.collapse', function (o) {
							$(o.target).parent().find(".panel-title a i:eq(1)").attr("class","fa fa-angle-down");
						});
						$('#menubar .panel-collapse').on('show.bs.collapse', function (o) {
							$(o.target).parent().find(".panel-title a i:eq(1)").attr("class","fa fa-angle-up");
						});
						
						$("#menubar .panel-title").on("click",function(){
							$("#menubar .panel-title").removeClass("active");
							$(this).addClass("active");
							
							if($(this).attr("mid")){
								var mid = $(this).attr("mid");
								$('#menubar .panel-collapse.in').collapse('hide');
								loadPage(mid);
								$("#menubar .panel-body li").removeClass("active");
							}
						});
						$("#menubar .panel-body li").on("click",function(){
							$("#menubar .panel-body li").removeClass("active");
							$(this).addClass("active");
							var mid = $(this).attr("mid");
							loadPage(mid);
						});
						if(isequipmentInit){
							loadPage(equId);
						}else if(menuId){
							loadPage(menuId)
						}else{
							loadIndex();
						}
					}
				});
				
			},
			beforeSend:function(req){
				//common.getUserSession(req);
			}
		});
		
	};

	/**����ҳ��**/
	function loadPage(mid){
		if(chartInterval){
			window.clearInterval(chartInterval);
		}
		if(chartInterval2){
			window.clearInterval(chartInterval2);
		}
		var data = menuMapData[mid]?menuMapData[mid]:null;
		if(data){
			common.setLocation(scopeMenuData,data.id);
			base.page.load({
				container:$(".ui-article"),
				url:data.url?data.url:"",
				isUnique:true,
				callback:function(){
					$(".ui-menubar #mid").val(mid);
				}
			});
		}
		
	};
	
	// �ǳ�
	var loginOut = function () {
		$("#logout").on("click",function(){
			window.location.href = $.base+"/index/logout";
		})
    }
	
	return {
		main:function(){
			base.page.clearBack();
			setMenu();
            loginOut();
		},
		loadPage:function(id){
			loadPage(id);
		}
	};
	
});