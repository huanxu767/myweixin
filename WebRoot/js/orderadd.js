var initPage = {
    //必要的数据参数  url中productCode的商品编码  product当前选中的商品 activity 活动相关数据
	option : {productCode:null,product:null,price:"",imageUrl:"",loginflag:false,gifts:"",name:"",sn:"",customerTel:"",cardProv:"",cardCity:"",psptId:"",customerAddr:"",customerName:"",postCode:"",fixedTel:"", activity:{type:1,cardNo:"",mobileNo:"",marketName:""}},
	init : function() {
		//解析出商品ID
		var productCode=request("productId");
		this.option.productCode=productCode;

		var sn =request("sn");
		this.option.sn=sn;
		this.option.customerTel=request("customerTel");
		this.option.cardProv=unescape(request("cardProv"));
		this.option.cardCity=unescape(request("cardCity"));
		this.option.psptId=request("psptId");
		this.option.customerAddr=unescape(request("customerAddr"));
		this.option.customerName=unescape(request("customerName"));
		this.option.postCode=request("postCode");
		this.option.fixedTel=request("fixedTel");
		
		userLogin.bindEvent({});
		
		if(sn==""){
			//没有使用营销案
		}else{
			//使用营销案
			$("#marketAddress").html("" +
					"<p><input type='radio' name='useAddress' onclick='useCardAddress()' />&nbsp;&nbsp;使用18元卡收货地址</p>"+
	       		    "<p><input type='radio' name='useAddress' checked='checked'  onclick='emptyAddress()' />&nbsp;&nbsp;使用新收获地址</p> "
			);
			
		}
		//初始化登录框
		this.drawUserLogin();
		
		//异步查询初始化页面 1，查询IP地址归属
		this.queryIpData();
		//异步查询初始化页面 2，查询商品详细数据
		this.queryData();
		//时间绑定
		this.bindEvent();
	},
	//查询IP地址归属
	queryIpData : function(data){
		commAjax({
		    'url':"/mall/ipQuery.do",
			success:function(data){
				//根据ip地址归属，判断收货地址是否输入身份证
				if(data["provinceName"]==""){
					data["provinceName"]="江苏省";
				}
				BindDefaultVal("province","city","area",data["provinceName"],"","");
				if(data["code"] == "1"){
					//本地的
					$("#sfzDiv").html("<p><em class='f-red1'>*</em>身份证号码：<input type='text' class='txt' name='deliveryCard' id='deliveryCard' maxlength='18' /><em class='f-gray3'> "+
			        				  	"江苏移动用户凭借身份证号码还可以额外享受话费包免费赠送活动，查看<a href='javascript:showHF();'>江苏用户话费包赠送活动详情</a> </em>" +
			        				  "</p>");
				}
			},
			error:function(){
				//alert("获取列表请求失败！");
			}
			});
	},
	//查询商品详细数据
	queryData : function(data) {
		commAjax({
//		    'url':"/demo/queryCacheMulti.do?keys="+initPage.option.productCode,
		    'url':"/queryCacheMulti?keys="+initPage.option.productCode,
			success:function(data){
				//如果商品不存在
//				data = data.data;
				var pNum=initPage.option.productCode;
				if(data[pNum]==undefined || data[pNum]==""){
					location.href="./nFind.html";
				}
				//解析数据
				initPage.parseData(data);
				//显示数据
				initPage.showData();
			},
			error:function(){
				//alert("获取列表请求失败！");
			}
			});
	},
	//解析商品数据
	parseData : function(data){
		var pNum=initPage.option.productCode;
		var products=data[pNum]["PRODUCTS"];
			//迭代出商品详情信息
			var gifts="" ;
			var p ;
			try{
			for(var i=0;i<products.length;i++){
				p=products[i];
			
					if(p["F_GOODS_NUM"] == initPage.option.productCode){
						//获得详情
						initPage.option.product=p;
						var priceT=p["F_SALE_PRICE"]+"";
						initPage.option.price=parseFloat(priceT).toFixed(2);
						initPage.option.name=p["F_ATOMS_SORT_NAME"];
						initPage.option.imageUrl=p["F_IMGS"][0][0]["F_PIC_URL"];
						//获取赠品
						initPage.toolsGifs(p);
						var jsonGifts = p["F_GIFS"];
						var gifts="";	
						for(var i=0;jsonGifts!= undefined && jsonGifts!="" && i<jsonGifts.length;i++){
							gifts += "<p>"+jsonGifts[i]["F_GOODS_NAME"]+"  ×"+jsonGifts[i]["F_GIFT_COUNT"]+"</p>";
						}
						initPage.option.gifts=gifts;
					}
			}
			}catch(e){
				
			}
	},
	//显示商品数据
	showData : function(){
		$("#productPrice").html(initPage.option.price);
		$("#productTprice").html((initPage.option.price-(-request("yhPrice"))).toFixed(2));
		$("#productName").html(initPage.option.name);
		if(initPage.option.gifts!=""){
			$("#gifts").append("<div  class='userExtra userCenter-order-largess' >"+
		              "<s class='icon'></s>"+initPage.option.gifts+
		              "</div>"
		              );
		}
		$("#productImage").attr("src",initPage.option.imageUrl);
		$("#totalPrice").append(initPage.option.price);
		$("#marketdesc").html(unescape(request("markedDesc")));
		$("#gartheringPrice").append((initPage.option.price-(-request("yhPrice"))).toFixed(2));
		
	},
	bindEvent : function() {
		$("#submitButton").bind("click",function(){
			
			initPage.addOrder();
		});
		$("#changeMobile").bind("click",function(){
			initPage.changeEmail();
		});
		$("#area").bind("click",function(){
			initPage.changeArea();
		});
		$("#city").bind("click",function(){
			initPage.changeArea();
		});
		$("#province").bind("click",function(){
			initPage.changeArea();
		});
		$("#closetip").bind("click",function(){
			$("#tipdiv").hide();
			mask.hide();
		});
		$("#showtip").bind("click",function(){
			mask.show();
			$("#tipdiv").show();
			
		});
		$("#loginBtn").bind("click",function(){
			initPage.loginUser();
		});
		$("#closeDiv").bind("click",function(){
			mask.hide();
			$("#loginDiv").hide();
		});
		$("#registBtn").bind("click",function(){
			initPage.registUser();
		});
		$("#changeUser").bind("click",function(){
			if($("#changeUser").html()=="使用已有邮箱作为查询账号"){
				$("#changeUser").html("使用新邮箱作为查询账号");
				$("#findPwd").html("找回密码");
				$("#pwd2").html("");
				$("#pwdtip").html("");
			}else{
				$("#changeUser").html("使用已有邮箱作为查询账号");
				$("#findPwd").html("");
				$("#pwd2").html("<em class='f-red1'>*</em>确认密码：<input type='password'  class='txt' name='passwordTemp' id='passwordTemp' maxlength='10' />");
				$("#pwdtip").html("密码6-10位");
			}
		});
		
	},
	//地址联动显示
	changeArea : function(){
		var provinceName=$("#province").find("option:selected").text();
		var cityName=$("#city").find("option:selected").text();
		var regionName=$("#area").find("option:selected").text();
		$("#showAddress").html(provinceName+","+cityName+","+regionName);
	},
	//提交下单
	submitDate : function(totalData){
		 //查询是否登录
		var agreeTips = $("#tip").attr("checked");
		 if(!agreeTips){
		  alert("请确认中国移动网上商城客户购前须知！");
		  $("#tip").focus();
		  return;
		 }
		  var h = $('input[name=invoiceHeadType]:checked').val();
		  if(h==1 && $("#invoiceHeadInfo").val()==""){
			alert("公司类型发票不能为空！");
			$("#invoiceHeadInfo").focus();
			return;
		  }
		
		  if($("#deliveryName").val()==""){
			  alert("收货人姓名不能为空！");
			  $("#deliveryName").focus();
			  return;
		  }
		  if($("#deliveryPostalcode").val()==""   ){
			  alert("邮政编码不能为空！");
			  $("#deliveryPostalcode").focus();
			  return;
		  }
		  if($("#deliveryPostalcode").val().length != 6 || isNaN($("#deliveryPostalcode").val())){
			  alert("邮政编码应为6位数字！");
			  $("#deliveryPostalcode").focus();
			  return;
		  }
		  
		  if($("#deliveryAddress").val()==""){
			  alert("收货地址不能为空！");
			  $("#deliveryAddress").focus();
			  return;
		  }
		  
		  if($("#deliveryMobile").val()=="" && $("#deliveryPhone").val()==""){
			  alert("收货地址手机和固话必须填写一个！");
			  if($("#deliveryMobile").val()==""){
				  $("#deliveryMobile").focus();
			}else{
				 $("#deliveryPhone").focus();
				}
			  return;
		  }
		  

		  if($("#deliveryMobile").val()!="" && ($("#deliveryMobile").val().length != 11 ||  isNaN($("#deliveryMobile").val())) ){
			  alert("手机号码格式不对！");
			  $("#deliveryMobile").focus();
			  return;
		  }
		  
		  if($("#deliveryCard").val() != undefined ){
			  if($("#deliveryCard").val()==""){
				  //no
				  alert("请输入身份证号码！");
				  $("#deliveryCard").focus();
				  return ;
			  }
			  if(($("#deliveryCard").val().length!=15 && $("#deliveryCard").val().length!=18) || isNaN($("#deliveryCard").val())){
				  alert("身份证号码格式不正确！");
				  $("#deliveryCard").focus();
				  return ;
			  }
			  
		  }
		  

		  if($("#streamCardMobile").val() != "" && !isNaN($("#streamCardMobile"))){
			  alert("领取流量卡的移动手机号码格式不正确！");
			  $("#streamCardMobile").focus();
			  return;
		  }
		  if(!initPage.option.loginflag){
			  if($("#email2").val() ==  ""){
				  alert("邮箱账号不能为空!");
				  $("#email2").focus();
				  return;
			  }
			  
			  if(! testEmail($("#email2").val())){
				  alert("邮箱账号格式不正确!");
				  $("#email2").focus();
				  return;
			  }
			  if($("#password2").val() == "" ){
				  alert("密码不能为空!");
				  $("#password2").focus();
				  return;
			  }
			  if($("#changeUser").html()!="使用新邮箱作为查询账号"){
				  if($("#password2").val() != $("#passwordTemp").val() ){
					  alert("2次密码输入不同!");
					  $("#password2").focus();
					  return;
				  }
			  }
			  if($("#password2").val().length <6 || $("#password2").val().length >10){
				  alert("密码最短6位最长10位!");
				  $("#password2").focus();
				  return;
			  }
		}
		  $("#submitButton").html("提交中...").attr("diabled",true);
		  
		commAjax({
		    'url':"/mall/orderaddAjax.do",
		    'data':totalData,
		    'type':"POST",
			success:function(data){
				if(data["code"]=="0000"){
					location="../mall/toPay.do?orderId="+data["orderId"]+"&productId="+initPage.option.productCode+"&payType="+data["payType"];
				}else{
					$("#submitButton").html("提交订单").attr("diabled",false);
					$("#errorShow").html(
							"<h2 style='color: red;'' > " +data["desc"]+
							"</h2>"
					 );
				}
			},
			error:function(){
				 $("#submitButton").html("提交订单").attr("diabled",false);
				alert("服务异常，请稍后再试！");
			}
			});
	},
	drawUserLogin:function(){
		
		var value = getCookie("mallLoginCookies");
		if("true"==value){
			//已经登录
			$("#useradmin").hide();
			initPage.option.loginflag=true;
		}else{
			$("#useradmin").show();
			initPage.option.loginflag=false;
		}
		/**
		commAjax({
		    'url':"/mall/islogin.do",
		    async:false,
			success:function(data){
				//已经登陆
				if(data["code"]=="0000"){
					$("#useradmin").hide();
					initPage.option.loginflag=true;
				}else{
					$("#useradmin").show();
					initPage.option.loginflag=false;
				}
			},
			error:function(){
				$("#useradmin").show();
				initPage.option.loginflag=false;
			}
			});
			**/
	},
	addOrder: function(){
		var productId = initPage.option.productCode;
		//收货人信息
		var deliveryName = $("#deliveryName").val();
		var deliveryCard = $("#deliveryCard").val();
		var deliveryProvince = $("#province").val();//deliveryProvince
		var deliveryCity = $("#city").val();//deliveryCity
		var deliveryCountry =  $("#area").val();
		var deliveryPostalcode = $("#deliveryPostalcode").val();
		var deliveryAddress = $("#deliveryAddress").val();
		var deliveryMobile = $("#deliveryMobile").val();
		var deliveryPhone = $("#deliveryPhone").val();
		//送货时间
		var distTime = $("input[name=distTime]:checked").val();
		//订单查询设置
		//支付方式
		var payType = $("input[name=payType]:checked").val();
		//发票信息
		var invoiceHeadType = $("input[name=invoiceHeadType]:checked").val();
		var invoiceHeadInfo = $("#invoiceHeadInfo").val();
		//用户信息
		var userEmail = $("#email2").val();
		var userPWD = $("#password2").val();
		
		var passwordTemp =$("#passwordTemp").val()+"";
		var streamCardMobile = $("#streamCardMobile").val();
		
		if($("#passwordTemp").val() == undefined || $("#passwordTemp").val() == "undefined"){
			passwordTemp = "";
		}
		// "name=John&location=Boston
		var totalData = "productId="+productId+"&"+
		"deliveryName="+deliveryName+"&"+
		"deliveryCard="+deliveryCard+"&"+
		"deliveryProvince="+deliveryProvince+"&"+
		"deliveryCity="+deliveryCity+"&"+
		"deliveryCountry="+deliveryCountry+"&"+
		"deliveryPostalcode="+deliveryPostalcode+"&"+
		"deliveryAddress="+deliveryAddress+"&"+
		"deliveryMobile="+deliveryMobile+"&"+
		"deliveryPhone="+deliveryPhone+"&"+
		"distTime="+distTime+"&"+
		"payType="+payType+"&"+
		"invoiceHeadType="+invoiceHeadType+"&"+
		"invoiceHeadInfo="+invoiceHeadInfo +"&" +
		"markedId="+request("markedId")+"&"+
		"markedDesc="+unescape(request("markedDesc"))+"&"+
		"cardno="+request("psptId")+"&"+
		"mobileno="+initPage.option.sn+"&"+
		"email="+userEmail+"&"+
		"password="+userPWD+"&"+
		"streamCardMobile="+streamCardMobile+"&"+
		"passwordTemp="+passwordTemp+"";
		
		
	initPage.submitDate(totalData);
	},
	submitLogin : function(totalData){
		commAjax({
		    'url':"/mall/login.do",
		    'data':totalData,
		    'type':"POST",
			success:function(data){
				if(data["code"]=="0000"){
					$("#loginDiv").hide();
					initPage.addOrder();
				}else{
					alert(data["desc"]);
				}
			},
			error:function(){
				alert("服务异常，请稍后再试！");
			}
			});
	},
	loginUser: function(){
		var email = $("#email").val();
		var password = $("#password").val();
		var randomCode = $("#randomCode").val();
	
		var totalData = "email="+email+"&"+
		"password="+password+"&"+
		"randomCode="+randomCode ;
		initPage.submitLogin(totalData);
	},
	registDate : function(totalData){
		//注册
		if($("#rPWD").val() != $("#rPWD1").val()){
			alert("密码2次输入的不同！");
			return;
		}
		commAjax({
		    'url':"/mall/regist.do",
		    'data':totalData,
		    'type':"POST",
			success:function(data){
			$("#submitForm").attr("diabled",false);
				if(data["code"]=="0000"){
					alert("注册成功！");
					$("#loginDiv").hide();
				}else{
					alert(data["desc"]);
				}
			},
			error:function(){
				$("#submitForm").attr("diabled",false);
				alert("服务异常，请稍后再试！");
			}
			});
	},
	registUser: function(){
		var email = $("#rEmail").val();
		var password = $("#rPWD").val();
		var randomCode = $("#rCode").val();
		var totalData = "email="+email+"&"+
		"password="+password+"&"+
		"randomCode="+randomCode ;
		initPage.registDate(totalData);
	},
	toolsGifs:function(p){
		var gifs=p["F_GIFS"];
		var c={"F_GIFT_COUNT":1,"F_GOODS_NAME":"20元流量卡"};
		var price=p["F_SALE_PRICE"];
		if(price<1000){
			c["F_GIFT_COUNT"]=1;
		}else if(price>=1000 && price <=2000){
			c["F_GIFT_COUNT"]=2;
		}else{
			c["F_GIFT_COUNT"]=3;
		}
		if(gifs==null){
			p["F_GIFS"]=[];
		}
		p["F_GIFS"].push(c);
	}
};
$(document).ready(function() {
	initPage.init();
});

function useCardAddress(){
	var sb =initPage.option.sn;
	$("#deliveryName").val(initPage.option.customerName);
	try{
		$("#deliveryCard").val(initPage.option.psptId);
	}catch(e){
		
	}
	$("#deliveryPostalcode").val(initPage.option.postCode);
	$("#deliveryAddress").val(initPage.option.customerAddr);
	$("#deliveryMobile").val(initPage.option.customerTel);
	$("#deliveryPhone").val(initPage.option.fixedTel);
	
	
	BindDefaultVal("province","city","area",initPage.option.cardProv+"省",initPage.option.cardCity,"");
	$("#showAddress").html(initPage.option.cardProv+","+initPage.option.cardCity+",市辖区");
	
}
function emptyAddress(){
	$("#deliveryName").val("");
	$("#deliveryCard").val("");
	$("#deliveryPostalcode").val("");
	$("#deliveryAddress").val("");
	$("#deliveryMobile").val("");
	$("#fixedTel").val("");
	$("#deliveryPhone").val("");

}
function refresh(obj) {
    document.getElementById('dCode').src = CONTEXT_PATH+"/mall/verificationcode.do?"+Math.random();
    document.getElementById('rImage').src =document.getElementById('dCode').src;
}
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ // enter 键
         //要做的事情
    }
}; 

function testEmail(str){
var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(reg.test(str)){
	  return true;
	}else{
	  return false;
	}
}

function  showHF(){
	mask.show();
	$("#activityShow").show();
}

function  closeHF(){
	mask.hide();
	$("#activityShow").hide();
}