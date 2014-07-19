var initPage = {
	option : {openid:null,count:0},
	init : function(){
		//初始化参数
		this.option.openid = request("openid");
		//检查用户是否已经存在，存在则查询个人信息
		this.queryUser();
		//绑定事件
		this.bindEvent();
		//查询所有用户

//		this.queryPlayers();
		// 绑定选择事件
					


	},
	bindEvent : function(){
		//$("#addRecord").bind("vclick",function(){
		//	initPage.addRecord();
		//});
		$("#name").bind("click",function(){
			$("#pageone").addClass("animated fadeOutLeft");
			$("#edit_name").addClass("animated fadeInRight").css("width",$(window).width()+"px").show();
		});
		
		$(".uploadImage").bind("click", function(){
		    $("#fileImage").click();
		});
		$("#fileImage").on("change",function(){
			console.log("filechange");
			initPage.ajaxFileUpload("/mobile/uploadHeadImage.do","fileImage",initPage.option.openid);
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
//		console.log("userMap:"+data.user.image_url);
		if(data.flag==false){
			alert("第三方编号为空。");
			return;
		}
		if(data.user.image_url != undefined){
			$("#headImage").attr("src","/"+data.user.image_url);
		}else{
			$("#headImage").attr("src","/images/weixin/defaultHead.png");
		}
		
		
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
	},
	ajaxFileUpload : function(url,fileId,openId){
//		console.log(openId+"ddd");
	    $.ajaxFileUpload({
		     url:url+"?openId="+initPage.option.openid,            //需要链接到服务器地址
		     secureuri:true,
		     fileElementId:fileId,                        //文件选择框的id属性
		     dataType: 'text',                                     //服务器返回的格式，可以是json
		     success: function (data, status){ 
//		    	 console.log(data);
		    	$("#fileImage").on("change",function(){
		    		initPage.ajaxFileUpload("/mobile/uploadHeadImage.do","fileImage",initPage.option.openid);
		    	});
		    	 datajson = data.substring(data.indexOf("{"),data.indexOf("}")+1);
		    	 datajson = eval("("+datajson+")");  
		    	 if(datajson.flag == true){
		    		 console.info(datajson.flag);
		    		 $("#headImage").attr("src","/images/uploadImages/"+datajson.totalPath);
		    	 }
		     },
		     error: function (data, status, e){
		    	 console.log("error"+data);
		     }
	       }
	    );
	}
}
$(document).ready(function() {
	initPage.init();
});
