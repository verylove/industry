/**
 * Created by Administrator on 2018/3/1 0001.
 */
define(["base","app/commonApp","echarts"],function(base,common,echarts){
    var basicColor=["#ffc63e","#4ae885","#5dc8ff","#ff67a4","#3c85ff","#37b3ff","#8b64d9","#67d3ff","#8ad197","#ff67a4","#ffb93e","#5384dc","#eece50"];
    var subjectColor=["#37b3ff","#8b64d9","#67d3ff","#8ad197","#ff67a4","#ffb93e","#5384dc","#eece50","#ffc63e","#4ae885","#5dc8ff","#ff67a4","#3c85ff"];
    var chart={
        industryOption:{
            color:basicColor,
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                show:false,
                orient: 'vertical',
                right:0,
                top:'middle',
                width:210,
                height:195,
                itemWidth:15,
                itemHeight:15,
                borderColor:"#dcdcdc",
                borderWidth:1,
                borderRadius:4,
                backgroundColor:'#f2f2ff',
                padding:[10,20,10,10],
                data:[]
            },
            graphic:{
                elements:[
                    {
                        type:"text",

                    }
                ]
            },
            series: [
                {
                    name:'行业分类',
                    type:'pie',
                    radius: ['45%', '70%'],
                    legendHoverLink:false,
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '15',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[]
                }
            ]
        },
        industryOption1:{
            color:['rgba(0,0,0,0.1)'],
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                show:false
            },
            graphic:{
                elements:[
                    {
                        type:"text",

                    }
                ]
            },
            series: [
                {
                    name:'行业分类',
                    type:'pie',
                    radius: ['45%', '57%'],
                    legendHoverLink:false,
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '15',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle:{
                        normal:{
                            shadowBlur:10,
                            shadowColor:'rgba(0,0,0,1)'
                        }
                    },
                    data:[]
                }
            ]
        },
        subjectOption:{
            color:subjectColor,
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                show:false,
                orient: 'vertical',
                right:0,
                top:'middle',
                width:210,
                height:195,
                itemWidth:15,
                itemHeight:15,
                borderColor:"#dcdcdc",
                borderWidth:1,
                borderRadius:4,
                padding:[10,20,10,10],
                backgroundColor:'#f2f2ff',
                data:[]
            },
            series : [
                {
                    name:'主题分类',
                    type:'pie',
                    radius : [20, 80],
                    center : ['25%', '50%'],
                    roseType : 'radius',
                    legendHoverLink:false,
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    lableLine: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:[]
                }
            ]
        },
        metadataOption : {
            color:["#cedd46","#f43c5f"],
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                itemWidth:10,
                itemHeight:10,
                borderRadius:10,
                right:'20%',
                data:['发布数量','撤销数量']
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                    axisTick:{
                        length:10,
                        lineStyle:{
                            color:"#bfbfbf"
                        }
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#37b3ff'
                        }
                    },
                    splitLine:{
                        show:true,
                        lineStyle:{
                            color:'#eee'
                        }
                    },
                    axisLabel:{
                        textStyle:{
                            color:'#959595',
                            fontFamily:"FZCXJT"
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name:'发布数量  VS  撤销数量',
                    minInterval:1,
                    nameTextStyle:{
                        fontSize:14,
                        color:'#007ee0',
                        vertticalAlign:'top'
                    },
                    nameGap:30,
                    axisLine:{
                        lineStyle:{
                            color:'#37b3ff'
                        }
                    },
                    axisTick:{
                        show:false
                    },
                    axisLabel:{
                        textStyle:{
                            color:'#959595',
                            fontFamily:"FZCXJT"
                        }
                    },
                    splitLine:{
                        lineStyle:{
                            color:'#eee'
                        }
                    },
                    splitArea:{
                        interval:'auto',
                        show:true,
                        areaStyle:{
                            color:['rgba(255,255,255,0)','rgba(249,249,255,1)']
                        }
                    }
                }
            ],
            series : [
                {
                    name:'发布数量',
                    type:'bar',
                    data:[0,0,0,0,0,0,0,0,0,0,0,0]
                },
                {
                    name:'撤销数量',
                    type:'bar',
                    data:[0,0,0,0,0,0,0,0,0,0,0,0]
                }
            ]
    }

};
    chart.getData = function () {
        var industryC = echarts.init(document.getElementById("industryChart"));
        var subjectC = echarts.init(document.getElementById("subjectChart"));
        $.ajax({
            url:$.base+"/statistics/elementData",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify({"year":"2018"}),
            success:function(result){
                chart.industry(result.industryData,industryC);
                chart.industry1(result.industryData);
                chart.subject(result.topicData,subjectC);
                chart.metadata(result.releaseData,result.revokeData);
                chart.operateLegend(result,industryC,subjectC);
            }
        })
    };
    chart.industry = function(industryData,industryC){
        //计算出所有的 industry的value
        var totalVal=0;
        var lis="";
        $.each(industryData,function (index,item) {
            chart.industryOption.legend.data.push(item.name);
            totalVal+=item.value;
        });
        chart.industryOption.series[0].data=industryData;
        industryC.setOption(chart.industryOption);
        $(".industry-box .legend-box").show();
        //渲染legend
        $.each(industryData,function (index,item) {
            var r = /^-?[1-9]\d*$/;
            var val = 0;
            var legendName=item.name;
            legendName.length> 5 ? legendName = item.name.substring(0,8)+"...":"";
            r.test(item.value/totalVal*100) ? val=item.value/totalVal*100:val=(item.value/totalVal*100).toFixed(2);
            lis += "<li class='operateLi' val='"+item.industryId+"' type='industry'><div class='operateDiv'>" +
                   "<span class='square'style='background:"+basicColor[index]+" '></span><span title='"+item.name+"' class='name'>"+legendName+
                   "</span><span class='per'>("+val+"%"+")</span></div></li>"
        });
        $(".legend").eq(0).find("ul").html(lis);
        base.scroll({
            container:$("#industry")
        });
    };
    chart.industry1 = function(industryData){
        var industry =  echarts.init(document.getElementById("hoverChart"));
        chart.industryOption1.series[0].data=industryData;
        industry.setOption(chart.industryOption1)
    };
    chart.subject = function(topicData,subjectC){
        var totalVal=0;
        var lis="";
        $.each(topicData,function (index,item) {
            chart.subjectOption.legend.data.push(item.name);
            totalVal+=item.value;
        });
        chart.subjectOption.series[0].data=topicData;
        subjectC.setOption(chart.subjectOption);
        $(".subject-box .legend-box").show();
        //渲染legend
        $.each(topicData,function (index,item) {
            var r = /^-?[1-9]\d*$/;
            var val = 0;
            var legendName=item.name;
            legendName.length> 5 ? legendName = item.name.substring(0,8)+"...":"";
            r.test(item.value/totalVal*100) ? val=item.value/totalVal*100:val=(item.value/totalVal*100).toFixed(2);
            lis+="<li class='operateLi' val='"+item.topicId+"' type='topic'><div class='operateDiv'>" +
                "<span class='square'style='background:"+subjectColor[index]+" '></span><span title='"+item.name+"' class='name'>"+legendName+
                "</span><span class='per'>("+val+"%"+")</span></div></li>"
        });
        $(".legend").eq(1).find("ul").html(lis);
        base.scroll({
            container:$("#subject")
        });
    };
    chart.metadata = function(releaseData,revokeData){
        var metadata =  echarts.init(document.getElementById("metaDataChart"));
        //发布数量
        if(releaseData.length>0){
            var arr=[0,0,0,0,0,0,0,0,0,0,0,0];
            $.each(releaseData,function(index,item){
                arr[Number(item.time2)-1] = item.count;
            });
            chart.metadataOption.series[0].data=arr;
        }
        //撤销数量
        if(revokeData.length>0){
            var arr=[0,0,0,0,0,0,0,0,0,0,0,0];
            $.each(revokeData,function(index,item){
                arr[Number(item.time2-1)] = item.count;
            });
            chart.metadataOption.series[1].data=arr;
        }
        metadata.setOption(chart.metadataOption)
    };
    chart.operateLegend = function (result,industryC,subjectC) {
        //触发legend 行业
        $(".dataStatisticalAnalysis").unbind();
        $(".dataStatisticalAnalysis").on('mouseenter','.operateDiv',function(event){
            var e = event || window.event;
            e.stopPropagation();
            var operate = $(this).parents("ul").attr("operate");
            var text = $(this).parent().find(".name").attr("title");
            $(".per").css("color","#333");
            $(this).parent().find(".per").css("color","#69c0ff");
            operate =="industry" ? chart.triggerAction(result.industryData,text,operate,industryC):chart.triggerAction(result.topicData,text,operate,subjectC);
        }).on("mouseleave",".operateDiv",function (event) {
            var e = event || window.event;
            e.stopPropagation();
            var legend = [];
            var operate = $(e.target).parents("ul").attr("operate");
            $(".per").css("color","#333");
            if(operate=="industry"){
                $.each(result.industryData,function (index,item) {
                    legend.push({name: item.name});
                });
                //取消高亮行为
                industryC.dispatchAction({
                    type:"downplay",
                    batch:legend
                });
            }else{
                $.each(result.topicData,function (index,item) {
                    legend.push({name: item.name});
                });
                //取消高亮行为
                subjectC.dispatchAction({
                    type:"downplay",
                    batch:legend
                });
            }

        });
        $(".operateLi").off().on("click",function () {
            localStorage.setItem("type", $(this).attr("type"));
            localStorage.setItem("val", $(this).attr("val"));
            common.load($.base+"/resciPublish/resciPublishView");
        })
    };
    chart.triggerAction = function(arr,highName,operate,check) {
        var legend = [];
        $.each(arr,function (index,item) {
            legend.push({name: item.name});
        });
        //图标依然保持全选
        check.dispatchAction({
            type: 'legendSelect',
            batch: legend
        });
        //触发高亮行为
        check.dispatchAction({
            type: 'highlight',
            name: highName
        });
    };
    return {
        main:function(){
            chart.getData();
        }
    }
});
