var initPage = {
	option : {openid:null},
	init : function(){
	},
	bindEvent : function(){
		
	},
	winRanking :function(list){
		var historyHtml = "";
		var date = "";
		for(var j = 0;j < list.length;j++){
			date = list[j].
			historyHtml = "<li data-role='list-divider'>星期五，5月1日，2014 <span class='ui-li-count'>3</span></li>";
			
			historyHtml += " <li><a href='otherInformation.html?id="+list[j].id+"' data-transition='slide' data-ajax='false'>";
			historyHtml += " 	<img src='../images/uploadImages/small/"+list[j].image_url+"' width='180px' height='180px' class='head_image'></img>";
			historyHtml += "	<h2>"+list[j].NAME+"</h2>";
			historyHtml += "    <p>胜率："+list[j].winper+"% 赢了："+list[j].totalmoney+"</p>";
			historyHtml += "    </a>";
			historyHtml += " </li>";
		}
		historyHtml += " </ol>";
		$("#content").html(historyHtml).trigger('create');
	}
}

$(document).ready(function() {
	initPage.init();
});
