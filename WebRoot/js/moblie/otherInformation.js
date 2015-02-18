var initPage = {
	option : {id:null},
	init : function(){
		this.option.id = request("id");
		this.getOherPermation();
		this.bindEvent();
		this.showChart();
	},
	bindEvent : function(){
		$("#showDetail").bind("click",function(){
			initPage.showDetail();
		});
	},
	
	getOherPermation : function(){
		commAjax({
		    'url':"/mobile/getUser.do",
		    data:"id="+initPage.option.id,
			success:function(data){
				initPage.initInformation(data);
			},
			error:function(){
				
			}
			});
	},
	initInformation :function(data){
		$("#headImage").attr("src","../images/uploadImages/small/"+data.image_url);
		$("#name").html(data.name);
		$("#mobileNo").html("号码: "+data.moblie);
		$("#godName").html("昵称: "+data.god_number);
		$("#winLose").html("胜: "+data.wintimes+" 输:"+data.losetimes);
		$("#actTimes").html((data.wintimes+data.losetimes)+"场    合计赢："+data.winmoney+"元" );
		$("#sign").html(data.signature);
//		$("#call").attr("href","tel:"+data.moblie);
//		$("#sms").attr("href","sms:"+data.moblie);
	},
	showDetail :function(){
		location.href = "../mobile/myHistory.html?openid="+this.option.id;
	},
	showChart :function(){
		commAjax({
		    'url':"/record/getMyRecordsChart.do",
		    data:"id="+initPage.option.id+"&currentPage=1&pageSize=20",
			success:function(data){
				initPage.initChart(data);
			},
			error:function(){
				
			}
		});
	},
	initChart :function(data){
//		console.info(JSON.stringify(data));
//		console.info(JSON.stringify(data.yList));
		$('#container').highcharts({
			chart: {
//                type: 'line'
				type:'column'
            },
            title: {
                text: '近20场纪录'
            },
            legend: {
				align: 'right',
				layout: 'vertical',
				verticalAlign: 'top',
				itemMarginBottom: 0,
				floating: true,
	 			itemStyle: {
	 				'fontWeight': 'normal'
	 			}
			},
			credits: {
	               enabled: false
	        },
			plotOptions: {
				line:{
					events :{
						click:function(){
							//alert('x='+event.point.x + ", y=" + event.point.y + ", extra=" + event.point.extra);
						}
					}
				}
			},
			tooltip:{
				formatter:function(){
					return this.point.extra.replace(/,/g,"");
				}
			},
		    yAxis: {
            	max:300,
	            min:-100,
	            title: {
	                text: '钞票',
	                align: 'high',
	                margin: -30,
	                y: -10,
	                rotation: 0
	            }
	        },
	        xAxis: {
            	tickInterval:1,
                tickmarkPlacement:'on',
            	labels: {'rotation': -45,step:3},
                categories: data.xList
            },
			series: [{
				name: ' ',
				data: data.yList
			}]
		});
	}
}

$(document).ready(function() {
	initPage.init();
});

