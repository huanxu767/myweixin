jQuery.fn.extend(
{
	showLoadingInner: function(text)
    {
    	$("body").hideLoadingInner();
		this.attr("disabled","disabled");
		var windowWidth = $(window).width(); 
	    // 计算机屏幕长度  
	    var windowHeight = $(window).height(); 
	    var padLeft=windowWidth/2-5;
	    var padTop=windowHeight/2-5;
		var  loadingDom = $("<div class='cube-loader-block' style='opacity: 0.7;width:80px;height:50px;left:"+padLeft+"px;top:"+padTop+"px;z-index: 6; '>"
	    		+"<img class='cube-loader-icon' src='data:img/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAABGBAMAAACDAP+3AAAAHlBMVEX\/\/\/+3t7e6urq4uLi4uLi5ubm5ubm5ubm5ubm5ubm/fxe2AAAACXRSTlMAIEaMjaPH8PN2+20BAAAA80lEQVR4XsWWLQ4CMRCFgRCwGDzHAIfCcwMEB8BxABQaw9yWAE1HfOS9bjahI799See187OTjjE7XM8bLZluI+K2kpp1vOMoNbuP5iGzuX80T5XRPL6xF3YWRXOqhHaWRXOphHZSUwjt5FmV0E7NmQb5iQZ5RJISTDUJNbwEnsXLZM4k9E7COwThW5DwTUlYGyB/6ClypgcOm7Aveqpw0VPJRU8lRxn8KhiUEwqvTePPaszZe/d32PYW/k1RA11KkcT3ju9B38t+JvjZMmRG+VnnZ6afvX6GN+8Cv1P8bvI7btiupMFRuzsNjv2XgB1R/P3iBSZwz1CJGJLVAAAAAElFTkSuQmCC'>"
				//+"<div id='txt_tip'>"+text+"</div>"
				+"</div>");
				$("body").append(loadingDom);
				$(".body").trigger("create");
		 $("body").popupDiv();
    }
    ,hideLoadingInner: function()
    {
    	$("body").hideDiv();
    	$(".cube-loader-block").remove();
    }
    
    ,popupDiv:function() { 
    var windowWidth = $(document).width(); //$("body").width(); 
    var windowHeight = $(document).height(); //$("body").height(); 
//     if($("body").width()<$(window).width())
//    {
//    	 windowWidth = $("window").width(); 
//    }
//    
//    // 计算机屏幕长度  
//    if($("body").height()<$(window).height())
//    {
//    	 windowHeight = $("window").height(); 
//    }
    
    if($(document).width()<$(window).width())
    {
    	 windowWidth = $("window").width(); 
    }
    
    // 计算机屏幕长度  
    if($(document).height()<$(window).height())
    {
    	 windowHeight = $("window").height(); 
    }
    
    // 添加并显示遮罩层  
    $("<div id='bg'></div>").width(windowWidth) 
            .height(windowHeight).click(function() { 
                //hideDiv(div_id); 
            }).appendTo("body").fadeIn(200); 
}
	/*隐藏弹出DIV*/ 
	,hideDiv:function() { 
	    $("#bg").remove(); 
	} 
    ,resizeLoadingInner:function() {
        if($(".cube-loader-block").length>0)
        {
       		// $("body").showLoadingInner($("#txt_tip").html());	
       		 $("body").showLoadingInner();	
        }
	}

});

var instance_id;//活动实例ID
var actId;//活动ID
var handle;//处理流程界面
var empNo;//工号
var proNo;//项目编号
var appNoContract;//开票申请编号
var sup_no;//查询供应商历史评价信息编号
var projectChangeApprovalFlag;
var specialCircs="";
var hasSpecial="false";
//初始化流程上下文
function initProgressContext(){
	var param = getQueryString();
	 $.post("../../../weChat/todo/checkSend",
	        	{emp_no:param.empNo,app_no:param.appNo,actInsID:param.instance_id,act_id:param.actId},
	        	function(data,status){
	        		if (status == "success") {
	        			var result = data.ajax_data;
						$("#proName").html(result.appName);
						$("#actName").html(result.actName);
						$("#appNo").html(param.appNo);
						actId=param.actId;
						handle=result.query_comp;
						empNo=param.empNo;
						instance_id=param.instance_id;
						if(result.outMap!=null){
							if(result.outMap.PRO_NO!=null){
								proNo=result.outMap.PRO_NO;
							}
							if(result.outMap.APP_NO_CONTRACT!=null){
								appNoContract=result.outMap.APP_NO_CONTRACT;
							}
							if(result.outMap.SUP_NO!=null){
								sup_no=result.outMap.SUP_NO;
							}
							if(handle=="approvalBusinessAuditing"){//项目立项-商务部审批
								if(result.outMap.PRO_CATEGORY!=null && result.outMap.PRO_CATEGORY=="02"){//特殊情况
									hasSpecial="true";
								}
								setTabNavigateViewData(0,tabs,getParam);
							}
							if(handle=="approvalLastAuditing"){//项目立项-领导审批
								if(result.outMap.PRO_CATEGORY!=null && result.outMap.PRO_CATEGORY=="02"){//特殊情况
									hasSpecial="true";
									specialCircs=result.outMap.SPECIAL_CIRCS;
								}
								setTabNavigateViewData(0,tabs,getParam);
							}
							if(handle=="approveModOperationAuditing" && result.outMap.flag!=null && result.outMap.flag!=""){//项目变更情况
								projectChangeApprovalFlag=result.outMap.flag;
								if(result.outMap.SPECIAL_CIRCS!=null && result.outMap.SPECIAL_CIRCS!=""){
									specialCircs=result.outMap.SPECIAL_CIRCS;
								}
								/*if(result.outMap.flag=="2"){//项目变更特殊情况
									projectChangeApprovalFlag="01";
								}else if(result.outMap.flag=="3"){//领导审核含特殊情况
									projectChangeApprovalFlag="02";
									specialCircs=result.outMap.SPECIAL_CIRCS;
								}else{
									projectChangeApprovalFlag="03";
								}*/
								setTabNavigateViewData(0,tabs,getParam);
							}
						}
	        		}
	        	},'json'
	     );
}

//页面添加锚点
function appendAnchor(){
	$(window).scroll(function(){  //返回顶部
        if ($(window).scrollTop()>100){
            $("#back-to-top").fadeIn(1500);
        } else {
            $("#back-to-top").fadeOut(1500);
        }
    });
    //当点击跳转链接后，回到页面顶部位置
    $("#back-to-top").click(function(){
        $('body,html').animate({scrollTop:0},1000);
        return false;
    });
}
//初始化tab容器
function initTabNavigateView(){
	var slider =
	  Swipe(document.getElementById('slider'), {
	    auto: 0, //0为不自动播放
	    continuous: true,
	    callback: function(pos) {	
	    	if (pos != prePos) {
	    		switchSearchPanel(pos);
	    	}
	    	$('body,html').animate({scrollTop:0},1000);
			if ($(".swipe-wrap .wrap").eq(pos).children().html() == null 
				|| $(".swipe-wrap .wrap").eq(pos).children().html().trim() == '') {
				//alert(pos);
				setTabNavigateViewData(pos,tabs,getParam);
			}
	    }
	  });
	  
	$(".tab_title ul li").click(function(){
		index = $(".tab_title ul li").index(this);
		$(this).addClass("selected").siblings().removeClass("selected");
		//slider.slide(index);
		if (index > prePos) {
			for (var i = prePos+1; i <= index; i++) {
				slider.slide(i);
			}
		} else {
			for (var i = prePos - 1; i >= index; i--) {
				slider.slide(i);
			}
		}
		
	}); 
}
//切换tab页
function switchSearchPanel(pos) {
	//alert(pos + " == " + prePos );
	if(!$(".u .scrol").is(":animated")){
		//alert(pos + " == " + prePos + " == " + len);
		if (pos > prePos) {//右移
			if (pos > 1 && pos < len - 1) {
				$(".u .scrol").animate({left : "-=" + scrollWidth +"px"},400);
			}
		} else {//左移
			if (pos > 0 && pos < len - 2) {
				$(".u .scrol").animate({left : "+=" + scrollWidth +"px"},400);
			}
		}
	}
	var array = $(".u .scrol").children();
	//$(".equal .row").children();
	$.each(array, function(key, node) {
		$(node).removeClass('selected');
		//$(node).hide();
	});
	$(array.eq(pos)).addClass('selected');
	prePos = pos;
};

//设置tab页数据
function setTabNavigateViewData(index,tabs,getParamCallback){
	var total = 0;
	var curPageNum = 0;
	var totalArr = $(".swipe-wrap .wrap").eq(index).children().eq(0).find("input");
	if (totalArr.length > 0) {
		total = $(totalArr[0]).val();//总页数
		curPageNum = $(totalArr[totalArr.length - 1]).val();//当前页
		if (parseInt(curPageNum) >= parseInt(total)) {
			$("#load_more").hide();
			$("#msg").show();
			$("#msg").fadeOut(1500,function(){
				$("#msg").hide();
			});
			return;
		}
	}
	var url;
	url = tabs[index];
    showloading();	
    var data = getParamCallback();
    data.currentPage = curPageNum;
	$.post(url,data,function(data,status){
		if(data != ""){
			//alert(data);
			var array = $(".swipe-wrap .wrap").eq(index).children().eq(0).find("input");
			if (array.length > 0) {
				$.each(array, function(key, node) {
					$(node).remove();
				});
			}
			var target = $(".swipe-wrap .wrap").eq(index).children().eq(0);
			target.append(data); 
 			target.trigger("create");
        	if (index != 0) {
        		array = $(".swipe-wrap .wrap").eq(index).children().eq(0).find("input");
        		if (array.length > 0) {
        			var total = $(array[0]).val();//总页数
					var curPage = $(array[array.length - 1]).val();//当前页
		        	if (curPage < total) {
						$("#load_more").show();
		        	} else {
		        		$("#load_more").hide();
		        	}
        		}
        	}
		} else {
			if(curPageNum == 0){
				$(".swipe-wrap .wrap").eq(index).children().eq(0).append("<div class='pagebar' style='display:block;'>没有相应的结果信息</div>");
				$(".swipe-wrap .wrap").eq(index).children().eq(0).trigger("create");
			} else {
				if (index != 0) {
					$("#load_more").hide();
					$("#msg").show();
					$("#msg").fadeOut(1500,function(){
						$("#msg").hide();
					});
				}
			}
		}
        hideloading();
    },"html");
}

//提交审批结果
function submitApproval(){
	if(handle!="executeTaskManageUndoTasks"){
		var appr_rslt = $("input:radio[name=approvalResult]:checked").val();
		if(appr_rslt == undefined){
			msgAlert("请填写审批结果,同意或不同意。");
			return;
		}
		if(appr_rslt == "02" && $("#appr_opinion").val()==""){
			msgAlert("请填写审批意见。");
			return;
		}
		
		var mannerism;
    	var special_circs;
		if((hasSpecial=="true" && handle=="approvalBusinessAuditing") 
			|| (projectChangeApprovalFlag=="2" && handle=="approveModOperationAuditing")){
			if($('#specialCheck').prop('checked')){
				if($("#special_info").val()==""){
					msgAlert("请填写特殊情况。");
					return;
				}else{
					mannerism="1";
					special_circs=$("#special_info").val();
				}
			}
		}
	}

	showloading();
	$.post("../../../weChat/todo/dispatchProcess",{
		act_id:actId,
		handle:handle,
		app_no:$("#appNo").html(),
		emp_no:empNo,
		instance_id:instance_id,
		appr_rslt:appr_rslt,
		appr_opinion:$("#appr_opinion").val(),
		mannerism:mannerism,
		special_circs:special_circs
		},
		function(data,status){
			if(data.ajax_flag="success"){
				var handleDept;
				var handleRole;
				var handleEmp;
				var nextAction = data.ajax_data.nextActName;
				//打回操作
				
				if(data.ajax_data.type == "1"){
					handleRole = data.ajax_data.handerMap.hander_Role;
					handleEmp = data.ajax_data.handerMap.hander_Emp;
					handleDept = data.ajax_data.handerMap.hander_Dept;
				}else if(data.ajax_data.type == "2"){
					handleEmp = data.ajax_data.nextHand;
				}
				alertDispatchSuccessMsg(data.ajax_data.type,{nextAction:nextAction,handleDept:handleDept,handleRole:handleRole,handleEmp:handleEmp},function(){
					//alert("--------------");
					//window.history.go(-1);
				});
			}else{
				alert(ata.ajax_msg);
			}
        	hideloading();
    	},"json");
}

//屏幕锁
function showloading(){
	var loadingDom = '<div class="loading-ui-mask"><div class="loading-ui"><div class="cui-breaking-load"><div class="cui-w-loading"></div><i class="cui-white-logo"></i></div></div></div>';
	$("body").append(loadingDom);
}
//解锁
function hideloading(){
	$(".loading-ui-mask").remove();
}

//重绘loading
function resizeLoading() 
	{
		$("body").resizeLoadingInner();
    }
    
//横屏时重新调整loading位置
$(window).bind('orientationchange', function(e){
	resizeLoading();
 });
$(document).ready(function () {
	bindTapEvent();
});  

//面包屑切换
function switchPage(url,transition){
	if(!transition){
		transition = "slide";
	}
	$.mobile.changePage( url, {
		  transition: transition,
		  changeHash: true,
		  showLoadMsg:true,
		  type:"post"
	});
}

//绑定选择客户档案事件
function bindSelecedEvent(){
	$(".cons_info").bind("vclick", function() {
		//选中客户 客户户号
        var currentConsNo = $(this.children[0]).find("font").text();
        //选中客户 客户名称
        var currentConsName = $(this.children[1]).find("font").text();
        //选中客户 供电单位编码
        var currentOrgNo = $(this.children[0]).find("input").val();
        //选中客户 用电地址
        var currentElecAddr = $(this.children[2]).find("font").text();
        var currentConsProfile = {consNo:currentConsNo,consName:currentConsName,orgNo:currentOrgNo,elecAddr:currentElecAddr};
        loadData(currentConsProfile);
        window.history.go(-1);
   });
}

//绑定mobile tap事件切换背景色
var currentTapDom;
function bindTapEvent(){
	$(".tap_block").bind("vmousedown", function() {
		if(currentTapDom){
			currentTapDom.removeClass("row_tap_block");
		}
		currentTapDom = $(this);
        $(this).addClass("row_tap_block");
   });
   $(".tap_block").bind("vmouseup", function() {
        $(this).removeClass("row_tap_block");
   });
}

function closeMessage(){
	
	$("#errorMessage").hide();
}

function showMessage(msg){
	$("#textMessage").text(msg);
	$("#errorMessage").show();
}
//QQ浏览器插件 屏蔽分享按钮
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	//WeixinJSBridge.call('hideOptionMenu');
});
//QQ浏览器插件 屏蔽前进后退按钮
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	//WeixinJSBridge.call('hideToolbar');
});
//获取QueryString的数组
function getQueryString(){
	var result = location.search;
    if(result == null){
        return {};
    }
    result = result.replace("?","");
    var params = result.split("&");
    var paramObject = {};
    for(var i = 0; i < params.length; i++){
        var tmp = params[i].split("=");
        if(tmp.length == 2){
        	paramObject[tmp[0]]=tmp[1];
        }
    }
    return paramObject;
}
//显示提示信息
function showErrorMsg(msg){
	$(".error").html("*"+msg);
	$(".error").show();
}
//清除提示信息
function clearErrorMsg(){
	$(".error").hide();
	$(".error").html();
}

 //异步动态加载
function suyAsyncLoad(filename,filetype){
    if(filetype == "js"){
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",filename);
    }else if(filetype == "css"){
        var fileref = document.createElement('link');
        fileref.setAttribute("rel","stylesheet");
        fileref.setAttribute("type","text/css");
        fileref.setAttribute("href",filename);
    }
   if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
 }
//Base64加密
function Base64() {
	// private property
	_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	// public method for encoding
	this.encode = function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = _utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output +
			_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
			_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		return output;
	}
 
	// public method for decoding
	this.decode = function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = _keyStr.indexOf(input.charAt(i++));
			enc2 = _keyStr.indexOf(input.charAt(i++));
			enc3 = _keyStr.indexOf(input.charAt(i++));
			enc4 = _keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = _utf8_decode(output);
		return output;
	}
 
	// private method for UTF-8 encoding
	_utf8_encode = function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
		return utftext;
	}
 
	// private method for UTF-8 decoding
	_utf8_decode = function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while ( i < utftext.length ) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}

function serializeJSON($){
	var params = $.serialize().split("&");
	var jsonString = "{";
	for(var i = 0;i < params.length;i++){
		if(i != 0){
			jsonString+=",";
		}
		var tmp = params[i].split("=");
		var name = tmp[0];
		var value;
		if(tmp.length == 2){
			value = tmp[1];
		}else{
			value="";
		}
		jsonString+=name+":\""+decodeURI(value)+"\"";
	}
	jsonString+="}";
	return eval("(" + jsonString + ")");
}

function serializeJSONString($){
	var params = $.serialize().split("&");
	var jsonString = "{";
	for(var i = 0;i < params.length;i++){
		if(i != 0){
			jsonString+=",";
		}
		var tmp = params[i].split("=");
		var name = tmp[0];
		var value;
		if(tmp.length == 2){
			value = tmp[1];
		}else{
			value="";
		}
		jsonString+=name+":\""+decodeURI(value)+"\"";
	}
	jsonString+="}";
	return jsonString;
}
//传递流程成功弹出框
function alertDispatchSuccessMsg(type,array,callback){
	var msgDom='<div class="msg_body">';
	if(type!="3"){
		msgDom+='<article class="msg_content"><img src="../../../images/icon/msg_icon.png"><span style="vertical-align: 80%;">活动传递成功</span></article>'+
		'<table>'+
		'	<tbody>'+
		'		<tr>'+
		'			<td>下一活动</td>'+
		'			<td>处理部门</td>'+
		'			<td>处理角色</td>'+
		'			<td>处理人</td>'+
		'		</tr>';
		if(array instanceof Array){
			for(var i = 0;i < array.length;i++){
				var nextAction = array[i].nextAction==undefined||array[i].nextAction==""?"--":array[i].nextAction;
				var handleDept = array[i].handleDept==undefined||array[i].handleDept==""?"--":array[i].handleDept;
				var handleRole = array[i].handleRole==undefined||array[i].handleRole==""?"--":array[i].handleRole;
				var handleEmp =  array[i].handleEmp==undefined||array[i].handleEmp==""?"--":array[i].handleEmp;
				msgDom += '<tr>'+
				'<td>'+nextAction+'</td>'+
				'<td>'+handleDept+'</td>'+
				'<td>'+handleRole+'</td>'+
				'<td>'+handleEmp+'</td>'+
				'</tr>';
			}
		}else{
			var nextAction = array.nextAction==undefined||array.nextAction==""?"--":array.nextAction;
			var handleDept = array.handleDept==undefined||array.handleDept==""?"--":array.handleDept;
			var handleRole = array.handleRole==undefined||array.handleRole==""?"--":array.handleRole;
			var handleEmp =  array.handleEmp==undefined||array.handleEmp==""?"--":array.handleEmp;
			msgDom += '<tr>'+
			'<td>'+nextAction+'</td>'+
			'<td>'+handleDept+'</td>'+
			'<td>'+handleRole+'</td>'+
			'<td>'+handleEmp+'</td>'+
			'</tr>';
		}
		msgDom += '</tbody></table>';
	}else{
		msgDom+='<article class="msg_content"><img src="../../../images/icon/msg_icon.png"><span style="vertical-align: 80%;">活动传递成功,流程结束</span></article>';
	}
	msgDom += '</div>';
	$(".content").empty();
	$(".content").append(msgDom);
}

function msgAlert(msg){
	var msgDom='<div id="_MsgBox_"><div class="ui_mask_black" style="height:'+
	document.body.scrollHeight+'px;width:'+document.body.scrollWidth+'px;position: fixed;background:#474747;opacity:0.7;"></div>'+
	'<div class="msg_box">'+
	'<div class="msg_title">提示</div>'+
	'<div class="msg_body">'+
	'<article class="msg_content" style="padding-top:10px;">'+msg+'</article>'+
	'</div>'+
	'<div style="margin-top: 15px;text-align: center;">';
	msgDom +='	<button class="message_box_btn" onclick="$(\'#_MsgBox_\').remove();">确 定</button>';
	msgDom +='</div>'+
	'</div></div>';
	$("body").append(msgDom);
	$(".msg_box").css("margin-left",($("body").width()-270)/2+"px");
}

function closeMsgBox(){
	//$("#_MsgBox_").remove();
	window.history.go(-1);
}

//获得本周的开始日期
function getWeekStartDate(date) { 
	var weekStartDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay()); 
	return weekStartDate; 
}

//获得本周的停止日期 
function getWeekEndDate(date) { 
	var weekEndDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() + (6 - date.getDay())); 
	return weekEndDate; 
}

//格局化日期：yyyy-MM-dd 
function formatDate(date) { 
	var myyear = date.getFullYear(); 
	var mymonth = date.getMonth()+1; 
	var myweekday = date.getDate();
	
	if(mymonth < 10){ 
		mymonth = "0" + mymonth; 
	} 
	if(myweekday < 10){ 
		myweekday = "0" + myweekday; 
	} 
	return (myyear+"-"+mymonth + "-" + myweekday); 
}
