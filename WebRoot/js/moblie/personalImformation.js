var initPage = {
	option : {openid:null},
	init : function(){
		//初始化参数
		this.option.openid = request("openid");
		//检查用户是否已经存在，存在则查询个人信息
		
		//绑定事件
		//this.bindEvent();
		//查询所有用户
		//this.queryPlayers();
	},
	bindEvent : function(){
		$("#addRecord").bind("vclick",function(){
			initPage.addRecord();
		});
	},
	//检查用户是否已经存在，存在则查询个人信息
	queryUser : function(){
		commAjax({
		    'url':"/user/queryUser.do",
		    'date':"openid="+openid,
			success:function(data){
				//alert(data.map.users[0].name);
				initPage.initPlaysersSeleted(data.map.users);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
		});
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
