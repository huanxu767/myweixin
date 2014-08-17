var initPage = {
	option : {id:null},
	init : function(){
		this.option.id = request("id");
		this.getOherPermation();
	},
	bindEvent : function(){
		
	},
	
	getOherPermation : function(){
		commAjax({
		    'url':"/mobile/getUser.do",
		    data:"id="+initPage.option.	id,
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
		$("#call").attr("href","tel:"+data.moblie);
		$("#sms").attr("href","sms:"+data.moblie);
	}
}

$(document).ready(function() {
	initPage.init();
});

