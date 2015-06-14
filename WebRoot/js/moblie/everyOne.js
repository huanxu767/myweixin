var initPage = {
	option : {},
	init : function(){
		initPage.queryUsers();
	},
	bindEvent : function(){
		
	},
	showUsers :function(data){
		var userHtml = "";
		var dateArray;
		var list = data.list;
		for(var j = 0;j < list.length;j++){
			var tempData = list[j];
			var tempHtml = "<div onclick=myInformation('"+tempData.id+"')><img src='"+"/images/uploadImages/small/"+
							tempData.image_url+"'><p>"+tempData.real_name+"<span>"+tempData.counts+"</span></p></div>"
			userHtml += tempHtml;
		}
//		http://127.0.0.1:8080/mobile/otherInformation.html?id=110011
		$("title").html("一个都不能少("+list.length+")");
		$("#content").html(userHtml);
		$("#recordNum").html(data.recordNum);
		$("#recordMoney").html(data.recordMoney);
	},
	queryUsers : function(){
		commAjax({
		    'url':"/record/getEveryOne.do",
			success:function(data){
				initPage.showUsers(data);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
			});
	}
}
function myInformation(url){
	location.href = "/mobile/otherInformation.html?id="+url;
}
$(document).ready(function() {
	initPage.init();
});
