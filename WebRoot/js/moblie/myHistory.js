var initPage = {
	option : {option:null},
	init : function(){
		this.option.openid = request("openid");
		initPage.queryHistory();
		
	},
	bindEvent : function(){
		
	},
	historyRank :function(list){
		var historyHtml = "";
		var date = "";
		var lastDate = "";
		var currentDate = "";
		var length = list.length;
		var dateArray;
		historyHtml += "<ul data-role='listview' data-inset='true' >"
		for(var j = 0;j < list.length;j++){
			//14-07-27 05:12:11 PM 0
			date = list[j].date;
			dateArray = date.split(" ");
			currentDate = dateArray[0];
			if(currentDate != lastDate){
				lastDate = currentDate;
				historyHtml += "<li data-role='list-divider'>"+initPage.formateDate(date)+"<span class='ui-li-count'>"+length+"</span></li>";
			}
			length = length-1; 
			historyHtml += "<li><a href='#' ><h2>"+list[j].place+"</h2>"; 
			historyHtml += "<p>"+list[j].content+"</p>"; 
			historyHtml += "<p class='ui-li-aside'><strong>"+dateArray[1].substring(0,5)+"</strong>"+dateArray[2]+"</p>"; 
			historyHtml += "</a></li>"; 
		}
		historyHtml += "</ul>"
//		console.info(historyHtml);
		$("#content").html(historyHtml).trigger('create');
	},
	formateDate:function(date){
		var week = {"0":"星期天","1":"星期一","2":"星期二","3":"星期三","4":"星期四","5":"星期五","6":"星期六"};
		var array = date.split(" ");
		var day = week[array[3]];
		var yearMonthDayArray = array[0].split("-");
		return day +","+parseInt(yearMonthDayArray[1])+"月"+yearMonthDayArray[2]+"日"+",20"+yearMonthDayArray[0];
	},	//查询所有用户
	queryHistory : function(){
		commAjax({
		    'url':"/record/getMyHistory.do",
		    'data':"openid="+this.option.openid,
			success:function(data){
				initPage.historyRank(data.list);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
			});
	}
}

$(document).ready(function() {
	initPage.init();
});
