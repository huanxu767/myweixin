/**
 * web上下文的根路径
 */
var CONTEXT_PATH = '';
/**
 * 实现javascript中的trim方法类似java中trim方法
 * @return String
 */
String.prototype.trim= function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};

/**
 * ajax统一方法
 * @param option json对象 包括{type: url: async: data: dataType: success: error: }
 * @return
 */
function commAjax(option){
	var type=option['type']==undefined?"POST":option['type'];
	var url=option['url'];
	if(url.indexOf("?")>0){
		url=url+"&math="+Math.random();
	}else{
		url=url+"?math="+Math.random();
	}
	url=CONTEXT_PATH+url;
	var async=option['async']==undefined?true:option['async'];
	var data=option['data']==undefined?{}:option['data'];
	var dataType=option['dataType']==undefined?"json":option['dataType'];
	var success=$.isFunction(option['success'])?option['success']:function(){};
	var error=$.isFunction(option['error'])?option['error']:function(){};
	var successEx=function(json){if(json.success==false && json.resp_code=="404"){alert(json.resp_desc);location.href=CONTEXT_PATH+"/mall/login.html";}success(json);};
	$.ajax({
		type:type,
		url:url,
		async:async,
		data:data,
		dataType:dataType,
		success:successEx,
		error:error
	});
}

function dateFormat(str){
	if(str==null||(str.length!=14 && str.length!=16)){
		return str;
	}
	var y=str.substring(0,4);
	var m=str.substring(4,6);
	var d=str.substring(6,8);
	var h=str.substring(8,10);
	var mi=str.substring(10,12);
	var s=(str.length==16?str.substring(12,14):"00");
	return y+"-"+m+"-"+d+" "+h+":"+mi+":"+s;
}

/**
 * 获得url中的参数数据
 */
request=function(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {}
	for (i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', value, expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = cookie.substring(name.length + 1);
                    break;
                }
            }
        }
        return cookieValue;
    }
};



var mask = {
	    //蒙板显示
		show : function() {
			this.Qmask.height($(document).height()).show();
		},
		//蒙板隐藏
		hide: function() {
			this.Qmask.hide();
	    },
	    center:function($obj){
	    	var screenWidth = $(window).width();
	    	var screenHeight = $(window).height(); 
	    	var scrolltop = $(document).scrollTop();
	    	var objLeft = (screenWidth - $obj.width())/2 ; 
	    	var objTop = (screenHeight - $obj.height())/2 + scrolltop; 
	    	$obj.css({left: objLeft + 'px', top: objTop + 'px','display': 'block','z-index':'9999999'});
	    },
	    Qmask:null,
	    //蒙板初始化
	    init:function(){
	    	this.Qmask=$(".mask");
	    	if(this.Qmask[0]==undefined){
	    		this.Qmask=$("<div style='display: none;' class='mask'></div>");
	    		$("body").append(this.Qmask);
	    	}
	    }
	};
	$(document).ready(function() {
		mask.init();
	});
	
	var userLogin={
			//用户已经登陆回调方法
			handlerY:function(){},
			//用户未登录回调
			handlerN:function(){},
			//用户登陆成功回调
			handlerLY:function(){},
			//登录失败回调
			handlerLN:function(){},
			//注册成功回调
			handlerRY:function(){},
			//注册失败回调
			handlerRN:function(){},
			//推出
			handlerTC:function(){},
			bindEvent:function(option){
				//登录事件
				userLogin.loginAndRe2();
				userLogin.isLogin();
				userLogin.loginAndRe();
				this.parExtends(option);
				
				$("#l_showImg").bind("click",function(){
					$("#l_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());	
				});
				$("#r_showImg").bind("click",function(){
					$("#r_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
				});
				
				$("#query_trophy_id").bind("click",function(){
					if(userLogin.isLogin()==true){
						location.href=CONTEXT_PATH+"/order/trophyPage.do";
						return;
					}
					mask.show();
					$(".artDialog_login_reg").show();
					var opx={
							"ly":function(){
								location.href=CONTEXT_PATH+"/order/trophyPage.do";
							},
							"tc":function(){
							},
							"ry":function(){
								location.href=CONTEXT_PATH+"/order/trophyPage.do";
							}
						};
					userLogin.parExtends(opx);
				});
				$("#query_order_id").bind("click",function(){
					if(userLogin.isLogin()==true){
						location.href=CONTEXT_PATH+"/mall/orderadmin.do";
						return;
					}
					mask.show();
					$(".artDialog_login_reg").show();
					var opx={
							"ly":function(){
								location.href=CONTEXT_PATH+"/mall/orderadmin.do";
							},
							"tc":function(){
							},
							"ry":function(){
								location.href=CONTEXT_PATH+"/mall/orderadmin.do";
							}
						};
					userLogin.parExtends(opx);
				});
			},
			isLogin:function(){
				var flags=false;
				commAjax({
				    'url':"/mall/islogin.do",
				    async:false,
					success:function(data){
						//已经登陆
						if(data["code"]=="0000"){
							flags= true;
							userLogin.yeLogin(data["email"]);
							//写入登录cookie
							addCookies("mallLoginCookies","true",'1');
						}else{
							userLogin.noLogin();
							flags= false;
							addCookies("mallLoginCookies","false",'1');
						}
					},
					error:function(){}
				});
				return flags;
		},
		//未登录渲染
		noLogin:function(){
			$("#top_login_id").unbind("click").bind("click",function(){
				$(".login_reg_cut li:eq(0)").click();
				mask.show();
				$(".artDialog_login_reg").show();
				var op={
						"ly":function(){
							window.location.reload();
						},
						"tc":function(){
							window.location.reload();
						},
						"ry":function(){
							window.location.reload();
						}
					};
				userLogin.parExtends(op);
				
			});
			$("#top_reg_id").unbind("click").bind("click",function(){
				$(".login_reg_cut li:eq(1)").click();
				mask.show();
				$(".artDialog_login_reg").show();
				var op={
						"ly":function(){
							window.location.reload();
						},
						"tc":function(){
							window.location.reload();
						},
						"ry":function(){
							window.location.reload();
						}
					};
				userLogin.parExtends(op);
			});
		},
		//已经登录
		yeLogin:function(name){
			$(".top-unlogin").hide();
			$(".top-logined").show();
			$("#top_name_id").html(name);
			$("#top_out_id").show().unbind("click").bind("click",function(){
				$(".top-logined").hide();
				$(".top-unlogin").show();
				userLogin.noLogin();
				commAjax({ 'url':"/mall/loginout.do",async:false});
				var op={
						"ly":function(){
							window.location.reload();
						},
						"tc":function(){
							window.location.reload();
						},
						"ry":function(){
							window.location.reload();
						}
					};
				userLogin.parExtends(op);
				userLogin.handlerTC();
			});
		},
		parExtends:function(option){
			this.handlerY=(option["y"]==undefined?this.handlerY:option["y"]);
			this.handlerN=(option["n"]==undefined?this.handlerN:option["n"]);
			this.handlerLY=(option["ly"]==undefined?this.handlerLY:option["ly"]);
			this.handlerLN=(option["ln"]==undefined?this.handlerLN:option["ln"]);
			this.handlerRY=(option["ry"]==undefined?this.handlerRY:option["ry"]);
			this.handlerRN=(option["rn"]==undefined?this.handlerRN:option["rn"]);
			this.handlerTC=(option["tc"]==undefined?this.handlerTC:option["tc"]);
		},
		loginAndRe:function(){
			var parent=$(".artDialog_login_reg");
			var bindEvent=function($p){
				$p.find(".close").unbind("click").bind("click",function(){
					$p.hide();
					mask.hide();
				});
				$p.find("#login_id").unbind("click").bind("click",function(){
					var email=$p.find("input[name='l_email']").val();
					var pass=$p.find("input[name='l_pass']").val();
					var code=$p.find("input[name='l_code']").val();
					if($.trim(email)==""){
						$("#loginError").html("<div class='j_message'><span class='error'>账号不能为空，请重新输入。</span></div>");
						return;
					}
					if($.trim(pass)==""){
						$("#loginError").html("<div class='j_message'><span class='error'>密码不能为空，请重新输入。</span></div>");
						return;
					}
					if($.trim(code)==""){
						$("#loginError").html("<div class='j_message'><span class='error'>验证码不能为空，请重新输入。</span></div>");
						return;
					}
					if(code.length!=4){
						$("#loginError").html("<div class='j_message'><span class='error'>验证码错误，请重新输入。</span></div>");
						return;
					}
					commAjax({
					    'url':"/mall/login.do",
					    data:{'email':email,'password':pass,'randomCode':code},
						success:function(data){
							//登陆成功
							if(data["code"]=="0000"){
								$(".artDialog_login_reg").hide();
								mask.hide();
								
								userLogin.yeLogin(email);
								userLogin.handlerLY(data);
							}else{
								$(".artDialog_login_reg").find("input[name='l_code']").val("");
								$(".artDialog_login_reg").find("#l_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
								$("#loginError").html("<div class='j_message'><span class='error'>"+data["desc"]+"</span></div>");
							}
						},
						error:function(){
						}
						});
				});
				$p.find("#reg_id").unbind("click").bind("click",function(){
					var email=$p.find("input[name='r_email']").val();
					var pass=$p.find("input[name='r_pass']").val();
					var pass2=$p.find("input[name='r_pass2']").val();
					var code=$p.find("input[name='r_code']").val();
					if($.trim(email)==""){
						$("#registError").html("<div class='j_message'><span class='error'>账号不能为空，请重新输入。</span></div>");
						return;
					}
					if($.trim(pass)==""){
						$("#registError").html("<div class='j_message'><span class='error'>密码不能为空，请重新输入。</span></div>");
						return;
					}
					if(pass!=pass2){
						$("#registError").html("<div class='j_message'><span class='error'>两次密码输入不一致，请重新输入。</span></div>");
						return;
					}
					if($.trim(code)==""){
						$("#registError").html("<div class='j_message'><span class='error'>验证码不能为空，请重新输入。</span></div>");
						return;
					}
					if(code.length!=4){
						$("#registError").html("<div class='j_message'><span class='error'>验证码错误，请重新输入。</span></div>");
						return;
					}
					
					commAjax({
					    'url':"/mall/regist.do",
					    data:{'email':email,'password':pass,'randomCode':code},
						success:function(data){
							//注册成功
							if(data["code"]=="0000"){
								$(".artDialog_login_reg").hide();
								mask.hide();
								userLogin.yeLogin(email);
								userLogin.handlerRY(data);
							}else{
								$(".artDialog_login_reg").find("input[name='r_code']").val("");
								$(".artDialog_login_reg").find("#r_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
								$("#registError").html("<div class='j_message'><span class='error'>"+data["desc"]+"</span></div>");
							}
						},
						error:function(){
						}
						});
				});
			};
			bindEvent(parent);
		},
		//登陆注册切换事件
		loginAndRe2:function(){
			$(".login_reg_cut li").click(function(){
			      var li_index=$(".login_reg_cut li").index(this);
			      var parent=$(".artDialog_login_reg");
			      if(li_index==0){
			    	  parent.find("#l_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
			      }else{
			    	  parent.find("#r_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
			      }
			      $(this).addClass("on").siblings().removeClass("on");
			      $(".login_reg_tabs .login_reg_tab").eq(li_index).show().siblings().hide();
				  $(".artDialog_login_reg h2 span").html($(this).html());
		        });
			$(".artDialog_login_reg").find("#l_showImg").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
		}
	};
	function changeImg(dom){
		$(dom).parents(".code").find("img").attr("src",CONTEXT_PATH+"/mall/verificationcode.do?r="+Math.random());
	}
	function subString(str, len, hasDot)
	{
	    var newLength = 0;
	    var newStr = "";
	    var chineseRegex = /[^\x00-\xff]/g;
	    var singleChar = "";
	    var strLength = str.replace(chineseRegex,"**").length;
	    for(var i = 0;i < strLength;i++){
	        singleChar = str.charAt(i).toString();
	        if(singleChar.match(chineseRegex) != null){
	            newLength += 2;
	        }else{
	            newLength++;
	        }
	        if(newLength > len){
	            break;
	        }
	        newStr += singleChar;
	    }
	    if(hasDot && strLength > len){
	        newStr += "..";
	    }
	    return newStr;
	}
/**
 * 
 * 临时json数据结构查看
 */
function JsonToStr(o) {
	var arr = [];       
	var fmt = function(s) {  
		if (typeof s == 'object' && s != null) {
			return JsonToStr(s);
		}
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
	};
	for (var i in o) {
		arr.push("'" + i + "':" + fmt(o[i]));       
	}
	return '{' + arr.join(',') + '}';       
}
//addCookies("mallLoginCookies","lookPage",'10');
function addCookies(objName,objValue,objHours){
	var str = objName + "=" + escape(objValue); 
	if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = objHours*24*60*60*1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
		}
		document.cookie = str;
}

function getCookie(objName){//获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for(var i = 0;i < arrStr.length;i ++){
		var temp = arrStr[i].split("=");
		if(temp[0] == objName) return unescape(temp[1]);
	}
}