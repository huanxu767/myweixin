var initPage = {
	option : {openid:null},
	init : function(){
		//初始化参数
		this.option.openid = request("openid");
		//检查用户是否已经存在，存在则查询个人信息
		this.queryUser();
		//绑定事件
		this.bindEvent();
		//查询所有用户
		//this.queryPlayers();
	},
	bindEvent : function(){
		//$("#addRecord").bind("vclick",function(){
		//	initPage.addRecord();
		//});
		$("#name").bind("click",function(){
			$("#pageone").hide();
			$("#edit_name").slideDown(1000);
			
		});
	},
	//检查用户是否已经存在，存在则查询个人信息
	queryUser : function(){
		commAjax({
		    'url':"/mobile/checkUser.do",
		    'data':"openid="+this.option.openid,
			success:function(data){
				console.info(data);
				initPage.initPersonalImformation(data);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
		});
	},
	//初始化下拉列表
	initPersonalImformation :function(data){
		if(data.flag==false){
			alert("第三方编号为空。");
			return;
		}
		
		
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
