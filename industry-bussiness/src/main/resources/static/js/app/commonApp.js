define(["base","datatables","tree","cookies"],function(base){
	var pageSize = 10;
	var self = {
		loginStatus:false,
		loginTimeEntity:null,
		loginTime:1800000,
		isEn:false,
		hasRole:true,
		menuEnData:[
			{"label":"首页概览","labelEN":"Home"},
			{"label":"注册管理","labelEN":"Register"},
			{"label":"请求方注册","labelEN":"Request"},
			{"label":"服务方注册","labelEN":"Service"},
			{"label":"审批管理","labelEN":"Approval"},
			{"label":"请求方审批","labelEN":"Request"},
			{"label":"服务方审批","labelEN":"Service"},
			{"label":"资源申请审批","labelEN":"Resource application"},
			{"label":"资源管理","labelEN":"Resource"},
			{"label":"资源编目","labelEN":"Directory"},
			{"label":"资源申请","labelEN":"Application"},
			{"label":"我申请的资源","labelEN":"My resource"},
			{"label":"元数据管理","labelEN":"Metadata"},
			{"label":"目录分类","labelEN":"Classify"},
			{"label":"字典管理","labelEN":"Dictionary"},
			{"label":"数据元管理","labelEN":"Metadata"},
			{"label":"单位管理","labelEN":"Unit"},
			{"label":"数据源管理","labelEN":"DataSource"},
			{"label":"日志审计","labelEN":"Log audit"},
			{"label":"操作日志","labelEN":"Operate"},
			{"label":"服务日志","labelEN":"Service"},
			{"label":"业务分析","labelEN":"Analysis"},
			{"label":"实时分析","labelEN":"Real-time"},
			{"label":"请求方分析","labelEN":"Request"},
			{"label":"服务方分析","labelEN":"Service"},
			{"label":"用户管理","labelEN":"User"},
			{"label":"用户管理","labelEN":"User"},
			{"label":"角色管理","labelEN":"Role"},
			{"label":"系统管理","labelEN":"System"},
			{"label":"消息管理","labelEN":"Message"},
			{"label":"设备管理","labelEN":"Device"},
			{"label":"NTP配置","labelEN":"NTP"},
			{"label":"端口转发","labelEN":"Port"},
			{"label":"黑名单管理","labelEN":"Blacklist"},
			{"label":"白名单管理","labelEN":"Whitelist"}
		],
		mergeMenuData:function(d){
			for(var i=0,j=d.length;i<j;i++){
				for(var i1=0,j1= self.menuEnData.length;i1<j1;i1++){
					if(d[i].label==self.menuEnData[i1].label){
						d[i].labelEN = self.menuEnData[i1].labelEN;
					}
				}
			}
		},
		/**国际化**/
		setInternationalization : function(){
			if(self.isEn){
				$("[en]").each(function(i,o){
					$(o).html($(o).attr("en"));
				});
			}
		},
		/**设置登录定时器**/
		setLoginTimeout:function(){//登录时效为30分钟
			if(self.loginTimeEntity){
				window.clearTimeout(self.loginTimeEntity);
			}
			self.loginStatus = true;
			self.loginTimeEntity = window.setTimeout(function(){
				self.loginStatus = false;
				self.login();
			},self.loginTime);
		},
		/**初始化**/
		init:function(){
			var data = {};
			$(".btn").attr("onclick","return false");
			$(".btn.back,.btn.cancel").on("click",function(){
				self.back();
                $(".mCSB_container").css("left",0);
			});
			
			if($(".ui-content-aside").not(".mCustomScrollbar").length>0){
				data.scroll = base.scroll({
					container:$(".ui-content-aside").not(".mCustomScrollbar"),
				});
			}
			data.date = base.form.date({
				element:$(".ui-form-date"),
				range:false,
				theme:"#0387db"
			});
            data.date = base.form.date({
                element:$(".ui-form-date-even"),
                range:true,
                theme:"#0387db"
            });
			return data;
		},
		/**获取cookie用户信息**/
		getUserInfo:function(){
			var loginInfo = self.getCookies();

			if(!loginInfo){
				window.location.href = $.base+"/login.html"
			}
			return loginInfo;
		},
		/**给内容区域加美化滚动条**/
		contentScroll:function(){
			if($(".ui-content-aside").not(".mCustomScrollbar").length>0){
                base.scroll({
                    container:$(".ui-content-aside").not(".mCustomScrollbar")
                });
			}
		},
		/**刷新当前页**/
		refresh:function(grid){
			grid.refresh();
		},
		
		/**查询列表**/
		search:function(grid){
			grid.reload();
		},
		/**表单重置**/
		reset:function(form,grid){
			base.form.reset(form,function(){
				if(grid){
					grid.reload();
				}
			});
		},
		/**统一化重置表单设置**/
		setResetForm:function(obj){
			$(obj).unbind("click").on("click",function(){
				base.form.reset($(this).parents(".ui-content").find("form")[0]);
			});	
		},
		/**统一化提交表单处理**/
		setSubmitForm:function(obj,callback,form){
			$(obj).unbind("click").on("click",function(){
				base.form.validate({
					form:$(form).length>0?$(form):$(this).parents(".ui-content").find("form")[0],
					checkAll:true,
					passCallback:function(){
						if(callback){callback();}
						
					}
				});
			});
		},
		/**设置表格上方按钮状态**/
		checkByGridButton:function(cbs){
			
			if(cbs&&cbs.length>0){
				var cd = $(cbs).filter(":checked");
				if(cd.length==1){
					$(".ui-grid-buttonbar .modify").removeClass("disabled");
					$(".ui-grid-buttonbar .delete").removeClass("disabled");
					$(".ui-grid-buttonbar .add").addClass("disabled");
					$(".ui-grid-buttonbar .adds").removeClass("disabled");
				}else if(cd.length>1){
					$(".ui-grid-buttonbar .modify").removeClass("disabled");
					$(".ui-grid-buttonbar .modify").addClass("disabled");
					$(".ui-grid-buttonbar .delete").removeClass("disabled");
					$(".ui-grid-buttonbar .add").addClass("disabled");
					$(".ui-grid-buttonbar .adds").addClass("disabled");
				}else{
					$(".ui-grid-buttonbar .add").removeClass("disabled");
					$(".ui-grid-buttonbar .modify").removeClass("disabled");
					$(".ui-grid-buttonbar .modify").addClass("disabled");
					$(".ui-grid-buttonbar .delete").removeClass("disabled");
					$(".ui-grid-buttonbar .delete").addClass("disabled");
					$(".ui-grid-buttonbar .adds").removeClass("disabled");
				}
			}
		},
		/**提交表单**/
		submit:function(option){
			var url = option.url?option.url:"";
			var params = option.params?option.params:null;
			var type = option.type?option.type:"get";
			var callback = option.callback?option.callback:null;
			var position = option.position?option.position:"top";
			var requestTip = base.requestTip({
				position:"center"
			});
			var async = option.async==false?false:true;
			if(url){
				base.ajax({
					url:url,
					params:params,
					type:type,
					async:async,
					timeout:10000,
					success:function(data){
						if(!data.success){requestTip.error(data.message);return;}
						else{
							requestTip.success("提交成功！");
							if(callback){
								callback(data);
							}
						}
						
					},
					beforeSend:function(){
						requestTip.wait();
					},
					error:function(){
						requestTip.error();
					}
				})
			}
			
		},
		/**设置当前位置**/
		setLocation:function(menuMapData,id){
			$(".ui-location ul").html("");
			var sf = {};
			var data = base.findParentToArray(menuMapData,id);
			if(data&&data.length>0){
				$(data).each(function(i,o){
					var node = document.createElement("li");
					if(self.isEn){
						
						$(node).html(o.labelEN);
					}else{
						$(node).html(o.label);
					}
					
					$(".ui-location ul").append(node);
					if(i!=data.length-1){
						$(node).append("<i class='fa fa-angle-right'></i>");
					}
				});
			}
		},
		/**获取星级**/
		getStar:function(starNumber){
			var s = "";
			for(var i = 1;i<=5;i++){
				if(i<=starNumber){
					s += "<i class='light fa fa-star'></i>";
				}else{
					s += "<i class='fa fa-star'></i>";
				}
			}
			return s;
		},
		/**加载页面**/
		loadPage:function(id){
			var data = menuMapData[mid]?menuMapData[mid]:null;
		},
		/**画chart图表**/
		drawChart:function(option){
			var sf = {};
			sf.chartContainer = option.chartContainer?option.chartContainer:null;
			sf.chartOption = option.chartOption?option.chartOption:null;
			sf.data = option.data?option.data:null;
			sf.callback = option.callback?option.callback:null;
			
			sf.draw = function(){
				sf.setOption();
				base.highCharts({
					container:sf.chartContainer,
					chartOption:sf.chartOption,
					callback:sf.callback
				});
			};
			sf.setOption = function(){
				sf.chartOption.series = sf.data;
			};
			
			sf.draw();
		},
		/**字符串数组转数字数组**/
		arrayStrToNumber:function(data){
			var d = [];
			$(data).each(function(i,o){
				d.push(Number(o));
			});
			return d;
		},
		/**设置panel的按钮**/
		setPanelButtonbar:function(){
			$(".ui-panel-buttonbar li").on("click",function(){
				$(this).parent().children("li").removeClass("active");
				$(this).addClass("active");
			});
		},
		/**设置表格的linkgroup**/
		setGridLinkGroup:function(){
			$(".ui-grid-linkGroup li").on("click",function(){
				$(this).parent().children("li").removeClass("active");
				$(this).addClass("active");
			});
		},
		/**转换树形数据**/
		mergeTreeData:function(data,rootId){
			var data = data;

			$(data).each(function(i,o){
				if(o.id==rootId){
					o.icon = "../images/0.png";
					o.open = true;
					return true;
				}else{
					var hasChild = false;
					$(data).each(function(i1,o1){
						if(o1.pid == o.id){
							o.icon = "../images/2.png";
							o.open = true;
							hasChild = true;
							return false;
						}
					})
					if(hasChild==false){
						o.icon = "../images/4.png";
					}
				}
			});


			return data;
		},
		/**表格过滤格式化1**/
		gridFilter:function(data){
            var d = $.parseJSON(data);
            switch(d.code){
            	case "0":
            		if(d.data == null){
            			d.recordsTotal = 0;
	            		d.recordsFiltered = 0;
	            		d.data = [];
            		}else{
            			d.recordsTotal = d.data.totalElements;
	            		d.recordsFiltered = d.data.totalElements;
	            		d.data = d.data.content;
            		}
            	break;
            	
            	default:
            		d.recordsTotal = 0;
            		d.recordsFiltered = 0;
            		d.data = [];
            		
            		var reqTip = base.requestTip();
            		reqTip.error(d.message);
            	break;
            }
            return JSON.stringify(d); 
       	},
       	/**表格过滤格式化2**/
       	gridFilters:function(data){
            var d = $.parseJSON(data);
            switch(d.code){
            	case "0":
            		if(d.data == null){
            			d.recordsTotal = 0;
	            		d.recordsFiltered = 0;
	            		d.data = [];
            		}else{
            			d.recordsTotal = d.recordsTotal;
	            		d.recordsFiltered = d.recordsFiltered;
	            		d.data = d.data;
            		}
            		
            	break;
            	
            	default:
            		d.recordsTotal = 0;
            		d.recordsFiltered = 0;
            		d.data = [];
            		
            		var reqTip = base.requestTip();
            		reqTip.error(d.message);
            	break;
            }
            return JSON.stringify(d); 
       	},
       	/**表格分页数据过滤**/
       	gridPageFliter:function(d,size){
       		d.page = d.start/d.length;
       		d.size = size?size:pageSize;
       	},
        /**获取req**/
       	getUserSession:function(req){
       		req.setRequestHeader("Test", "test");
       	},
       	/**GET方式获取表单参数**/
       	getParams:function(d,form,grid){
       		self.gridPageFliter(d);
       		var params = "";
			params = base.form.getParams($(form),true);
			var i =0;
			for(var key in d){
				switch(key){
					case "columns":
					case "search":
					case "draw":
					case "order":
					case "start":
						continue;
					break;
					default:
						if(i==0){
							if(params==""){
								params+=key+"="+d[key];
							}else{
								params+="&"+key+"="+d[key];
							}
							
						}else{
							params+="&"+key+"="+d[key];
						}
						
					break;
				}
				i++;
			}
			return params;
        },
        /**POST方式获取表单参数**/
        getPostParams:function(d,form,grid){
        	self.gridPageFliter(d);
        	var params = base.form.getParams($(form));
        	if(params){
        		d = $.extend(d,params);
        	}
        	return JSON.stringify(d);
        },
        /**初始化表格上按钮**/
        initButtonbar:function(buttonbar){
        	$(buttonbar).find(".add").show().removeClass("disabled");
        	$(buttonbar).find(".modify").removeClass("disabled").addClass("disabled");
        	$(buttonbar).find(".delete").removeClass("disabled").addClass("disabled");
        	$(buttonbar).find(".adds").show().removeClass("disabled");
        },
        /**统一化treeTable组件应用**/
        treeTable:function(setting){
        	var sf = {};
        	sf.tmpRow = null;
			sf.dg = function(data){
				if(data&&data.length>0){
					$(data).each(function(i,o){
						var row = document.createElement("tr");
						$(row).attr("data-tt-id",o.id);
						$(row).attr("data-tt-parent-id",o.pid);
						$(row).addClass("ui-treeTableChild");
						var s = "";
						
						$(setting.aoColumns).each(function(i1,o1){
							var type = o1.type?o1.type:null;
							switch(type){
								case "checkbox":
									s+="<td><input type='checkbox' class='cb' name='cb' level='"+o.level+"' value='"+o[o1.data]+"' pid='"+o.pid+"'/></td>";
								break;
								
								case "operation":
								    var addChildStr="";
								    if(o.level=="4"){
								    	addChildStr="";
								    }else{
								    	addChildStr="<button rid='"+o[o1.data]+"' level='"+o.level+"' class='btn btn-link blue addChild'  title='新增子级'><i class='fa fa-plus'></i></button>";
								    }
									s+="<td><button rid='"+o[o1.data]+"' class='btn btn-link blue modify' type='role' name='修改' title='修改'><i class='fa fa-pencil'></i></button>"+
									"<button rid='"+o[o1.data]+"' class='btn btn-link blue delete' type='role' name='删除' title='删除'><i class='fa fa-trash-o'></i></button>"+addChildStr+"</td>";
								break;
								
								default:
									
									s+="<td>"+(function(){return o[o1.data] ? o[o1.data] : "--"})()+"</td>";
									
									
								break;
							}
							
						});
						$(row).html(s);
		    			$(sf.tmpRow).after(row);
		    			sf.tmpRow = row;
						sf.dg(o.children);
					});
				}
			};
			var data = setting.json.data;
        	
        	$(data).each(function(i,o){
        		if(o.children&&o.children.length>0){
        			sf.tmpRow = $(setting.nTable).find("tbody tr[rootrow='"+i+"']");
        			sf.dg(o.children);
        		}
        	});
        	base.treeTable({
        		container:setting.nTable,
				setting:{
					expandable:true
				}
        	});
        },
        /**类型转换(数字转文本)**/
        typeSelect:function(para){
        	switch(para){
        		case '12':
        			return "文本";
        			break;
        		case '91':
        			return "日期";
        			break;
        		case '3':
        			return "数字";
        			break;
        		case '93':
        			return "时间";
        			break;
        		case '4':
        			return "整型";
        			break;
        		case '2004':
        			return "大字段";
        			break;
        	}
        },
         /**类型转换(文本转数字)**/
        typeSelectText:function(para){
        	switch(para){
        		case '文本':
        			return "12";
        			break;
        		case '日期':
        			return "91";
        			break;
        		case '数字':
        			return "3";
        			break;
        		case '时间':
        			return "93";
        			break;
        		case '整型':
        			return "4";
        			break;
        		case '大字段':
        			return "2004";
        			break;
        	}
        },
        /**获得cookies**/
        getCookies:function(){
        	var cookie = $.cookie("dssgUserInfo");
        	
        	if(cookie){
        		cookie = JSON.parse(cookie);
        	}
        	return cookie;
        }, 
       	/**行选择的样式**/
        selectedTr:function(example){
			example.find("tbody").on( 'click', 'tr', function () {
				$("tbody tr").removeClass("selected");
		        $(this).toggleClass('selected');
		    });
		},
		/**input设置disabled**/
		disableInput:function(e){
			$.each(e,function(i,o){
				$("input[name="+o+"]").attr("disabled","disabled");
				$("select[name="+o+"]").attr("disabled","disabled");
				$("."+o).attr("disabled","disabled");
			})
		},
		/**截取30个字符**/
		interceptString:function(e,m){
			var len = m ? m :30;
			if(e.length>len){
				return "<div title='"+e+"'>"+(e.substr(0,len)+'...')+"</div>"
			}else{
				return "<div title='"+e+"'>"+e+"</div>";
			}
		},
		/**获取表格option**/
		getGridOption:function(option){
			option=option?option:{};
			var paging = option.paging==false?false:true;
			var processing = option.processing==false?false:true;
			var info = option.info==false?false:true;
			var serverSide = option.serverSide==false?false:true;
			var searching = option.searching==true?true:false;
			var ordering = option.ordering==true?true:false;
			var lengthChange = option.lengthChange==true?true:false;
			return {
				processing:processing,
				serverSide:serverSide,
				searching:searching,
				ordering:ordering,
				lengthChange:lengthChange,
				info:info,
				paging:paging,
				ajax:{
					url:"../json/grid2.json",
					type:"get",
					contentType:"application/json",
					data:function(d){
						return common.getParams(d,$("#search-form"));
					}
				},
				columns:[],
				columnDefs:[],
		        drawCallback:function(setting){
		        	
		        }
			}
		},
		/**获取steps的高度 **/
		getStepsHeight:function(){
			var content = $(".ui-page-item.active .ui-content");
			var controlbar = content.find(".ui-content-controlbar");
			var h = content.innerHeight() - controlbar.innerHeight() -90;
			return h;
		},
		/**收拉效果**/
		pull:function(){
			base.pull({
				container:$(".ui-aside.left"),
				target:$(".ui-aside.right"),
				pullbtn:"../images/pullbtn.png",
				color:"#333",
				bg:"#ccc",
				hoverBg:"#bbb",
				border:"#666"
			});
		},
		/**tree组件**/
		tree:function(option){
			var data = option.data?option.data:null;
			var type = option.type?option.type:"get";
			var url = option.url?option.url:null;
			var params = option.params?option.params:null;
			var container = option.container?option.container:null;
			var defaultId = option.defaultId?option.defaultId:null;
			var expandAll = option.expandAll==false?false:true;
			var treeObj = null;
			var clickEvent = option.clickEvent?option.clickEvent:null;
			var checked = option.checked==true?true:false;
			var setting = option.setting?option.setting:{};
			var dataDef = option.dataDef?option.dataDef:"data";
			setting.data = {"simpleData": {"enable": true}};
			if(checked){
				setting.check = {"enable":true};
			}
			var callback = option.callback?option.callback:null;
			var dataCallback = option.dataCallback?option.dataCallback:null;
			if(callback){
				// callback.beforeClick = function(a,b,c){
				// 	debugger
				// 	$("#myAccordion .curSelectedNode").removeClass("curSelectedNode");
				// }
				setting.callback = callback;
				
			}
			setting.view = {
				fontCss: function(treeId, node){return node.font ? node.font : {};},
				nameIsHTML: true
			};
			if(container){
				$(container).addClass("ztree");
			}
			if(data){
				treeObj = base.tree({
					container:container,
					setting:setting,
					data:self.mergeTreeData(data[dataDef],"-1"),
					selectNodeId:defaultId,
					expandAll:expandAll
				});
			}else{
				if(url){
					base.ajax({
						url:url,
						params:params,
						type:type,
						async:false,
						success:function(data){
                            if(dataCallback){
                                data = dataCallback(data);
                            }
                            // var dat = self.mergeTreeData(data[dataDef],"-1");
                            // if(!dat){
                            //     dat=data;
                            // }
							if(data.data){
								data = data.data
							}
							treeObj = base.tree({
								container:container,
								setting:setting,
								data:data,
								selectNodeId:defaultId,
								expandAll:expandAll
							});
						},
						error:function(){
							self.login();
						}
					});
				}
			}
			
			return treeObj?treeObj.treeObj:null;
		},
		
		/**登录跳转**/
		login:function(){
			// window.location.href=$.base+"/login.html";
		},
		/**获取页面**/
		load:function(url,callback){
			callback = callback?callback:null;
			if(url){
				
				base.page.load({
					url:url,
					callback:function(){
						//$(".ui-article").children("div[type='pull']").hide();
						if(callback){
							callback();
						}
					}
				});
			}
		},
		/**返回功能**/
		back:function(){
			base.page.back();
			
		},
		/**数据库的显示切换**/
		apiSourceNameChange :function(name){
			return name=="local" ?"本地数据库":"第三方API"
		},
		/**画表格**/
		drawGrid:function(option){
			var container = option.container;
			var gridOption = option.gridOption;
			var tempOption = {};
			var idDef = option.idDef?option.idDef:"id";
			if(container&&gridOption){
				for(var key in gridOption){
					switch(key){
						case "drawCallback":
							tempOption.drawCallback = function(setting){
								self.role();
								gridOption.drawCallback(setting);
							}
						break;
						default:
							tempOption[key] = gridOption[key];
						break;
					}
				}
				return base.datatables({
					container:$(container),
					option:tempOption,
					nodataIcon:"<img src='../images/nodata.png'/>",
					filter:self.gridFilters,
					selectType:option.selectType?option.selectType:null,
					selectAttribute:option.selectAttribute?option.selectAttribute:null,
					callback:option.callback?option.callback:null,
					idDef:idDef,
					callback:function () {
                        $(".dataTables_paginate").on("click", "a", function() {
                            $("input[name='cball']").prop("checked",false);
                        });
                    }
				});
			}
			
		},
		/**设置编码规则**/
		setCodeRole:function(obj,level){
			var role = null;
			switch(level){
				case 0:
					
				break;
				
				case 1:
					role = "{en_number:true,required:true,length:1}";
				break;
				
				case 2:
					role = "{en_number:true,required:true,length:2}";
				break;
				
				case 3:
					role = "{en_number:true,required:true,length:3}";
				break;
				
				case 4:
					role = "{en_number:true,required:true,length:3}";
				break;
			}
			
			$(obj).attr("role",role);
		},
		/**统一化ajax处理**/
		ajax:function(option) {
			/*var myOption = {};
			for(var key in option){
				switch(key){
					case "beforeSend":
						var bs = function(req){
							option[key](req);
							var userInfo = self.getUserInfo();
						};
						myOption[key] = bs;
					break;
					
					default:
						myOption[key] = option[key];
					break;
				}
			}*/
			base.ajax(option);
		},
		/**角色权限控制**/
		role:function(){
			if(self.hasRole){
				if(scopeMenuData){
					var mid = $(".ui-menubar #mid").val();
					if(mid){
						for(var k1 in scopeMenuData){
							var dat = scopeMenuData[k1];
							
							if(dat.pId==mid||dat.pid==mid){
								if(dat.label){
									$(".ui-page-item:eq(0) .btn[type='role']").filter("[name='"+dat.label+"']").attr("role","active");
								}
								
							}
						}
						$(".ui-page-item:eq(0) .btn[type='role']").filter("[role!='active']").remove();
					}
					
				}
			}
		},
		/*****上传单位的错误信息提示****/
		getUploadInfo:function(e){
			switch(e){
        		case 'notnode':
        			return "请上传正确格式的接入节点证书";
        			break;
        		case 'notmanage':
        			return "请上传正确格式的管理节点证书";
        			break;
        		case 'uploadfail':
        			return "证书文件上传失败";
        			break;
        		case 'suffixfail':
        			return "文件格式不正确";
        			break;
        		case 'authfail':
        			return "证书文件解析过程中出现异常";
        			break;
        		case 'error':
        			return "证书解析失败,请上传正确证书";
        			break;
        		case 'compressionerror':
        			return "证书压缩包解压失败请核对证书文件，或联系管理员";
        			break;
        		case 'ioerror':
        			return "证书压缩包文件读取失败";
        			break;
        		case 'filereadfail':
        			return "业务文件读取失败...请联系管理员";
        			break;
        		case 'notcername':
        			return "压缩包中证书名称不合法，请联系管理员";
        			break;
        		default:
        		 	return "导入失败"
        	}
		},
		/**tab标签**/
		tab:function(option){
			var self = {};
			self.container = option.container?option.container:null;
			self.data = option.data?option.data:null;
			self.clickEvent = option.clickEvent?option.clickEvent:null;
			self.cls = option.cls?option.cls:null;
			self.height = option.height?option.height:40;
			self.size = option.size?option.size:7;
			if(!self.container){return;}
			if(!self.data||self.data.length==0){return;}
			base.pillTabs({
				container:self.container,
				data:self.data,
				clickEvent:self.clickEvent,
				size:self.size,
				cls:self.cls,
				itemHeight:self.height
			});
		},
		addLoadBg:function () {
			var html = '<div class="loading-bg"><img src="../images/5-121204193Q9.gif" alt=""></div>';
			$("body").append(html);
        },
		overLoadBg:function(){
			$(".loading-bg").remove();
		}
	};
	return self;
});