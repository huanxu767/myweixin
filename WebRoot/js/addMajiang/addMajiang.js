var initPage = {
	option : {},
	init : function(){
		this.initDatePiker();
		//绑定事件
		this.bindEvent();
		//查询所有用户
		this.queryPlayers();
	},
	bindEvent : function(){
		$("#addRecord").bind("vclick",function(){
			initPage.addRecord();
		});
	},
	//初始化 日期控件
	initDatePiker : function(){
		var currYear = (new Date()).getFullYear();
		var opt={};
		opt.end = {
				theme: 'android-ics light', //皮肤样式
		        display: 'bottom', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
				dateFormat: 'yyyy-mm-dd',
        		dateOrder: 'yymmdd'
			};
		opt.start = {
				theme: 'android-ics light', //皮肤样式
		        display: 'bottom', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
				dateFormat: 'yyyy-mm-dd',
        		dateOrder: 'yymmdd'
			};
			opt.start_time = {preset : 'date',onSelect:function(valueText, inst){
			    opt.end.minDate = new Date(valueText.replace(/-/g,"/"));
			}};
			$("#start_time").val('').scroller('destroy').scroller($.extend(opt['startTime'], opt['start']));
			opt.month = {preset : 'date',dateFormat:'yyyy-mm', dateOrder: 'yymm'};
			opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
			opt.datetime = {preset : 'datetime'};
			opt.time = {preset : 'time'};
			opt['default'] = {
				theme: 'android-ics light', 
		        display: 'bottom',
		        mode: 'scroller', 
				lang:'zh',
		        startYear:currYear - 10, 
		        endYear:currYear + 10 
			};
	},
	//查询所有用户
	queryPlayers : function(){
		commAjax({
		    'url':"/user/queryUsers.do",
			success:function(data){
				//alert(data.map.users[0].name);
				initPage.initPlaysersSeleted(data.map.users);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
			});
	},
	//初始化下拉列表
	initPlaysersSeleted :function(users){
		//console.info(users.length);
		var usersSeleted = "<option value='nochoice'>请选择</option>";
		for(var j = 0;j < users.length;j++){
			usersSeleted +="<option value='"+users[j].id+"'>"+users[j].name+"</option>";
		}
		var userNames = $("select[name='userName']");
		userNames.each(function(){
			$(this).append(usersSeleted);
		});
	},
	addRecord : function(){
//		console.info("form数据系列化："+$("form").serialize());
		initPage.loading();
		commAjax({
		    'url':"/user/test.do",
		    'data':$("form").serialize(),
			success:function(data){
				initPage.hide();
			},
			error:function(){
				//concole.info("新增记录出错了，怎么搞的");
				initPage.hide();
			}
			});
	},
	loading : function(){
		$("#loader").show();
		$.mobile.loading( "show", {
            text: "loading",
            textVisible: false,
            theme: 'a',
            textonly: false,
            html: ""
   		 });
	},
	hide : function(){
		$("#loader").hide();
		$.mobile.loading( "hide" );
	}
}

$(document).ready(function() {
	initPage.init();
});
