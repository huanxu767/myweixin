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
		//更改姓名
		$("#changeName").bind("click",function(){
			$("#edit_name").removeClass("animated fadeOutRight");
			$("#pageone").addClass("animated fadeOutLeft");
			$("#edit_name").addClass("animated fadeInRight").show();
			$("#userName").css("width",($(window).width()-29)+"px");
			$("#userName").val($("#name").html());
		});
		
		//更改缺神号
		$("#changeNo").bind("click",function(){
			$("#edit_No").removeClass("animated fadeOutRight");
			$("#pageone").addClass("animated fadeOutLeft");
			$("#edit_No").addClass("animated fadeInRight").show();
			$("#userNo").css("width",($(window).width()-29)+"px");
			if($("#no a").length>0){
				$("#userNo").val($("#no a").html());
			}else{
				$("#userNo").val($("#no").html());
			}
			
		});
		//更改号码
		$("#moblieNo").bind("click",function(){
			$("#edit_mobile").removeClass("animated fadeOutRight");
			$("#pageone").addClass("animated fadeOutLeft");
			$("#edit_mobile").addClass("animated fadeInRight").show();
			$("#userMobile").css("width",($(window).width()-29)+"px");
			if($("#mobile a").length>0){
				$("#userMobile").val(($("#mobile a").html()+"").replace(/-/g,""));
			}else{
				$("#userMobile").val(($("#mobile").html()+"").replace(/-/g,""));
			}
		});
		
		$("#mark").bind("click",function(){
			$("#edit_Mark").removeClass("animated fadeOutRight");
			$("#pageone").addClass("animated fadeOutLeft");
			$("#edit_Mark").addClass("animated fadeInRight").show();
			$("#userMark").css("width",($(window).width()-29)+"px").height(80);
			$("#userMark").val($("#idvalue").html().trim());
		});
		//上传图片
		$(".uploadImage").bind("click", function(){
		    $("#fileImage").click();
		});
		$("#fileImage").on("change",function(){
			console.log("filechange");
			initPage.ajaxFileUpload("/mobile/uploadHeadImage.do","fileImage",initPage.option.openid);
		});
		$("#headImage").bind("click", function(){
			console.log($("#headImage").attr("src"));
			loadImg($("#headImage").attr("src"));
		});
		$("#largeContainer").bind("click",function(){
			$("#largeContainer").addClass("animated bounceOut");
		});
		//取消更改姓名
		$("#cancelName").bind("click",function(){
			initPage.swipLeft("edit_name");
//			$("#userName").blur();	
		});
		$("#saveName").bind("click",function(){
			initPage.swipLeft("edit_name");
			$("#name").html($("#userName").val());
			//可以优化下  看是否改变
			initPage.updateName("1",$("#userName").val());
		});
		//取消更改姓名
		$("#cancelNo").bind("click",function(){
			initPage.swipLeft("edit_No");
		});
		$("#saveNo").bind("click",function(){
			initPage.swipLeft("edit_No");
			$("#no").html($("#userNo").val());
			//可以优化下  看是否改变
			initPage.updateName("2",$("#userNo").val());
		});
		
		$("#cancelMobile").bind("click",function(){
			initPage.swipLeft("edit_mobile");
		});
		$("#saveMobile").bind("click",function(){
			initPage.swipLeft("edit_mobile");
			$("#mobile").html(initPage.formateMobileNo($("#userMobile").val()));
			//可以优化下  看是否改变
			initPage.updateName("3",$("#userMobile").val());
		});
		
		$("#cancelMark").bind("click",function(){
			initPage.swipLeft("edit_Mark");
		});
		$("#saveMark").bind("click",function(){
			initPage.swipLeft("edit_Mark");
			$("#idvalue").html($("#userMark").val());
			initPage.updateName("4",$("#userMark").val());
		});
	},
	swipLeft : function(id){
		$("#pageone").removeClass("animated fadeOutLeft");
		$("#"+id).removeClass("animated fadeInRight");
		$("#pageone").addClass("animated fadeInLeft").show();
		$("#"+id).addClass("animated fadeOutRight");
	},
	updateName : function(type,value){
		commAjax({
		    'url':"/mobile/updateUser.do",
		    'data':"openid="+this.option.openid+"&type="+type+"&value="+value,
			success:function(data){
				console.info(data);
			},
			error:function(){
				//concole.info("用户查询出错");
			}
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
			$("#headImage").attr("src","/images/uploadImages/small/"+data.user.image_url);
		}else{
			$("#headImage").attr("src","/images/weixin/defaultHead.png");
		}
		if(data.user.name != undefined){
			$("#name").html(data.user.name);	
		}else{
			$("#name").html("小伙伴快起个名字吧");
		}
		if(data.user.god_number != undefined){
			$("#no").html(data.user.god_number);	
		}else{
			$("#no").html("这需要一个雀神号");
		}
		if(data.user.moblie != undefined && data.user.moblie!="0"){
			$("#mobile").html(initPage.formateMobileNo(data.user.moblie+""));	
		}else{
			$("#mobile").html("大侠号码多少");
		}
		if(data.user.signature != undefined ){
			$("#idvalue").html(data.user.signature);	
		}else{
			$("#idvalue").html("这家伙太懒，什么都没留下");
		}
		if(data.loseTimes != undefined ){
			$("#loseTimes").html(data.loseTimes);	
		}else{
			$("#loseTimes").html("0");
		}
		if(data.winTimes != undefined ){
			$("#winTimes").html(data.winTimes);	
		}else{
			$("#winTimes").html("0");
		}
		if(data.winTimes != undefined ){
			$("#winTimes").html(data.winTimes);	
		}else{
			$("#winTimes").html("0");
		}
		if(data.winMoney != undefined ){
			$("#winMoney").html(data.winMoney);	
		}else{
			$("#winMoney").html("0");
		}
		if(data.loseMoney != undefined ){
			$("#loseMoney").html(data.loseMoney);	
		}else{
			$("#loseMoney").html("0");
		}
		if(data.loseMoney != undefined ){
			$("#loseMoney").html(data.loseMoney);	
		}else{
			$("#loseMoney").html("0");
		}
		if(data.totalTimes != undefined ){
			$("#totalTimes").html(data.totalTimes);	
		}else{
			$("#totalTimes").html("0");
		}
		if(data.recordPer != undefined ){
			$("#recordPer").html(data.recordPer);	
		}else{
			$("#recordPer").html("0.00%");
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
	formateMobileNo : function(mobileNo){
		var newMobile = mobileNo.substring(0,3)+"-"+mobileNo.substring(3,7)+"-"+mobileNo.substring(7,12);
		console.log("ddd:"+newMobile);
		return newMobile;
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
//		    		 console.info(datajson.flag);
		    		 $("#headImage").attr("src","/images/uploadImages/small/"+datajson.totalPath);
		    	 }
		     },
		     error: function (data, status, e){
		    	 console.log("error"+data);
		     }
	       }
	    );
	}
}

var count = 0 ;
var loadImg = function(_id){
	var zWin =$(window);
	var wImage = $("#large_img");
	
	if(count == 0){
		var winWidth = zWin.width();
		var winHeight = zWin.height();
		var paddingLeft = parseInt((winWidth-46)/2);
		var paddingTop = parseInt((winHeight-46)/2);
		wImage.attr('src','/images/ajax-loader.gif').css('padding-left',paddingLeft).css('padding-top',paddingTop);
	}else{
		count++;
	}
	var imagsrc = _id.replace("/small","/big");
//	console.log(imagsrc);
	var imageObj = new Image();
	imageObj.onload = function(){
		
		var w = this.width;
		var h = this.height;
		var winWidth = zWin.width();
		var winHeight = zWin.height();
		var zpex = winWidth/winHeight;
		var imgpex = w/h; 
//		console.log("图片长宽："+w+"         "+h);
//		console.log("屏幕长宽："+winWidth+"        "+winHeight);
//		console.log("比："+zpex+"        "+imgpex);
		var realw = winHeight * w/h;
		var realh = winWidth * h/w;
		var paddingLeft = parseInt((winWidth-realw)/2);
		var paddingTop = parseInt((winHeight-realh)/2);
		wImage.css('padding-left',0).css('padding-top',0);
		if(zpex>imgpex){
			//设置图片的高  竖图
			wImage.attr('src',imagsrc).css('height',winHeight).css('padding-left',paddingLeft);
//			console.log("one:zpex>imgpex");
		}else{
			//设置图片的宽 横图
			wImage.attr('src',imagsrc).css('width',winWidth).css('padding-top',paddingTop);
			console.log("two:zpex<imgpex");
		}
	}
	imageObj.src = imagsrc;
	$("#largeContainer").removeClass("animated bounceIn bounceOut");
	$("#largeContainer").css({width:zWin.width(),height:zWin.height()}).addClass("animated bounceIn").show();
}


$(document).ready(function() {
	initPage.init();
});
