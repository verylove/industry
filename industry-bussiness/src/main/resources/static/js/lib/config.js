require.config({
	baseUrl:(function() {
	    var curWwwPath = window.document.location.href;
	    var pathName = window.document.location.pathname;
	    var pos = curWwwPath.indexOf(pathName);
	    var localhostPath = curWwwPath.substring(0,pos);
	    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    return(localhostPath+projectName);
	})() + "/js",
	shim: {
		jquery:["moment"],
		bootstrap:["jquery"],
		dateTimePicker:["jquery"],
		//datatables:["jquery","datatablesPagination"],
		jOrgChart:["jquery"],
		cityselect:["jquery"],
		carousel:['jquery'],
		easyui:['jquery'],
		jqScrollbar:['jquery','jqMousewheel'],
		jqMousewheel:["jquery"],
		jscroll:['jquery'],
		htEdgetype:['htTopo'],
		htAutolayout:['htTopo'],
		htDashflow:['htTopo'],
		htLive:['htTopo'],
		htHtmlNode:['htTopo'],
		binder:['jquery'],
		'template' : {
			exports:"template"
		},
		graphLayout:['jquery'],
		graph:["topo"],
		highCharts:{
			exports: "Highcharts",
      		deps: ["jquery"]
      	},
      	highChartsMore:{
      		exports: "highChartsMore",
      		deps: ["highCharts"]
      	},
		highCharts3D:{
			exports: "Highcharts3D",
      		deps: ["highCharts"]
      	},
      	gauge:["highCharts","highChartsMore"],
		exp:["highCharts"],
		highDark:["highCharts"],
        'baiduMap': {
            deps: ['jquery'],
            exports: 'baiduMap'
        },
   		echartsGl:["echarts"],
   		tree:['jquery'],
   		schedule:['jqueryUI'], 
   		calendar:['jqueryUI'],
   		calendarLocale:["calendar"],
   		fileinput:['jquery'],
   		'fileinputZh':['jquery',"fileinput"],
   		dateRange:['jquery'],
   		barrager:['jquery']
	},
	paths:{
		"jquery" : "lib/jquery-1.11.3.min",
		"async":"lib/async",
		"bootstrap":"lib/bootstrap.min",
		"template":"lib/handlebars-v4.0.8.min",
		"radialIndicator":"lib/radialIndicator.min",
		"bsDatatables":"lib/dataTables.bootstrap",
		"resDatatables":"lib/dataTables.responsive.min",
		"datatables":"lib/jquery.dataTables.min",
		"jqueryUI":"lib/jquery-ui.min",
		"carousel":"lib/carousel",
		"popover":"lib/popover",
		"echartsConfig":"lib/echartsConfig",
		"echarts" : "lib/echarts.min",
		"echarts2.0" : "lib/echarts2.0",
		"echartsGl":"lib/echarts-gl.min",
		"chinaGeo":"lib/map/chinaGeo",
		"jOrgChart":"lib/jquery.jOrgChart",
		"jqScrollbar":"lib/jquery.mCustomScrollbar.min",
		"radialIndicator":"lib/radialIndicator",
		"jqMousewheel":"lib/jquery.mousewheel.min",
		"jscroll":"lib/jscroll",
		"dateFormat":"lib/dateFormat",
		"graph":"lib/graph.editor",
		"topo":"lib/qunee-min",
		"graphLayout":"lib/graph.layout",
		"highCharts":"lib/highcharts",
		"highChartsMore":"lib/highcharts-more",
		"highCharts3D":"lib/highcharts-3d",
		"exp":"lib/exporting",
		"highDark":"lib/dark-unica",
		"gauge":"lib/solid-gauge",
		"date":"lib/laydate.min",
		"baiduMap":"http://api.map.baidu.com/api?v=2.0&ak=MCGlo2EFVeVl5w3jeGsbjIMGGClYLm2f",
		"base":"lib/base.min",
		"htTopo":"lib/ht",
		"htEdgetype":"lib/ht-edgetype",
		"htAutolayout":"lib/ht-autolayout",
		"htDashflow":"lib/ht-dashflow",
		"htLive":"lib/ht-live",
		"htHtmlNode":"lib/ht-htmlnode",
		"autoSelect":"lib/jquery.autoSelect",
		"three":"lib/three.min",
		"tree":"lib/jquery.ztree.all.min",
		"cookies":"lib/jquery.cookie",
		"treeTable":"lib/jquery.treetable",
		"calendar":"lib/fullcalendar",
		"calendarLocale":"lib/locale-all",
		"download":"lib/jquery.fileDownload",
		"fileinput":"lib/fileinput",
		"fileinputZh":"lib/fileinput-zh",
		"select2":"lib/select2.min",
		"fileUpload":"lib/ajaxfileupload.min",
		"dateRange":"lib/jquery.daterangepicker.min",
		"moment":"lib/moment.min",
		"date5.0":"lib/laydate5.0.min",
		"areaSelect":"lib/areaSelect.min",
		"qrcode":"lib/jquery.qrcode.min",
		"editor":"lib/editor-all-min",
		"barrager":"lib/jquery.barrager.min"
	},
	map: {
        '*': {
            'jquery': 'jquery-config'
        },
        'jquery-config': {
            'jquery': 'jquery'
        }
   }
});

define('jquery-config', ['jquery'], function(){
	var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0,pos);
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    $.base = localhostPath+projectName;
    //$.path = localhostPath;
    $.path = "http://192.168.230.219:8000";
    //$.path1=$.path+"/dssg/"+projectName+"/";
    $.path1 = $.path+"/vpnsp-frontend-manage/" ;
    return $;
});