var initPage = {
	option : {openid:null},
	init : function(){
		//this.getRanking();
		//$('ol').listview('refresh');
	},
	bindEvent : function(){
		
	},
	
	getRanking : function(){
		commAjax({
		    'url':"/record/getRanking.do",
			success:function(data){
				//赢钱
				initPage.winRanking(data.winlist);
				//输钱钱
				initPage.loseRanking(data.loseList);
				//胜率钱
				initPage.perRanking(data.perList);
			},
			error:function(){
				
			}
			});
	},
	winRanking :function(list){
		//console.info(users.length);
		var rankingHtml = "<ol data-role='listview'   >";
		for(var j = 0;j < list.length;j++){
			rankingHtml += " <li><a href='otherInformation.html?id="+list[j].id+"' data-transition='slide' data-ajax='false'>";
			rankingHtml += " 	<img src='../images/uploadImages/small/"+list[j].image_url+"' width='180px' height='180px' class='head_image'></img>";
			rankingHtml += "	<h2>"+list[j].NAME+"</h2>";
			rankingHtml += "    <p>胜率："+list[j].winper+"% 赢了："+list[j].totalmoney+"</p>";
			rankingHtml += "    </a>";
			rankingHtml += " </li>";
		}
		rankingHtml += " </ol>";
		$("#one").html(rankingHtml).trigger('create');
	},
	loseRanking :function(list){
		var rankingHtml = "<ol data-role='listview'   >";
		for(var j = 0;j < list.length;j++){
			rankingHtml += " <li><a href='otherInformation.html?id="+list[j].id+"' data-transition='slide' data-ajax='false'>";
			rankingHtml += " 	<img src='../images/uploadImages/small/"+list[j].image_url+"' width='180px' height='180px' class='head_image' ></img>";
			rankingHtml += "	<h2>"+list[j].NAME+"</h2>";
			rankingHtml += "    <p>胜率："+list[j].winper+"% 输赢："+list[j].totalmoney+"</p>";
			rankingHtml += "    </a>";
			rankingHtml += " </li>";
		}
		rankingHtml += " </ol>";
		$("#two").html(rankingHtml).trigger('create');
	},
	perRanking :function(list){
		var rankingHtml = "<ol data-role='listview'   >";
		for(var j = 0;j < list.length;j++){
			rankingHtml += " <li><a href='otherInformation.html?id="+list[j].id+"' data-transition='slide' data-ajax='false'>";
			rankingHtml += " 	<img src='../images/uploadImages/small/"+list[j].image_url+"' width='180px' height='180px' class='head_image'></img>";
			rankingHtml += "	<h2>"+list[j].NAME+"</h2>";
			rankingHtml += "    <p>胜率："+list[j].winper+"% 输赢："+list[j].totalmoney+"</p>";
			rankingHtml += "    </a>";
			rankingHtml += " </li>";
		}
		rankingHtml += " </ol>";
		$("#three").html(rankingHtml).trigger('create');
	}
}

$(document).ready(function() {
	initPage.init();
});
