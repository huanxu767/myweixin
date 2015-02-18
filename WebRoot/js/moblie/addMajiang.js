var initPage = {
	option : {openid:null},
	init : function(){
		//初始化参数
		this.option.openid = request("openid");
		
		this.initDatePiker();
		//绑定事件
		this.bindEvent();
		//查询所有用户
		this.queryPlayers();
		$("#openId").val(this.option.openid);
	},
	bindEvent : function(){
		$("#addRecord").bind("click",function(){
			initPage.addRecord();
		});
	},
	//初始化 日期控件
	initDatePiker : function(){
		var now = new Date();
	    $('#startTime').mobiscroll().datetime({
	        theme: 'android-ics light',
	        display: 'bottom',
	        minDate: new Date(now.getFullYear()-1, now.getMonth(), now.getDate()),
//	        dateFormat: 'yy/mm/dd',
	        lang:'zh'
//	        monthNames:['一月','二月','三月','一月','二月','三月','一月','二月','三月','一月','二月','三月']
//	        invalid: [ 'w0', 'w6', '5/1', '12/24', '12/25' ]
	    });
	},
	//查询所有用户
	queryPlayers : function(){
		commAjax({
		    'url':"/mobile/queryUsers.do",
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
		var userNames = $("select[name='playerId']");
		userNames.each(function(){
			$(this).append(usersSeleted);
		});
	},
	addRecord : function(){
		console.info("form数据系列化："+$("form").serialize());
		initPage.loading();
		commAjax({
		    'url':"/record/addRecord.do",
		    'data':$("form").serialize(),
			success:function(data){
				console.log("牌局添加成功");
				alert("牌局添加成功");
				$("form")[0].reset();
				initPage.hide();
			},
			error:function(){
				alert("添加牌局信息出错，请检查数据");
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
