var dianfei,chanzhi,date,nengxiao,counts=0;

//新增
function addRecord(){
	 dianfei = $("#dianfei").val();
	 chanzhi = $("#chanzhi").val();
	 date = $("#start_time").val();
	 counts++;
	 $("#content_record").prepend(ognizeShowHtml());
	 $("#record_"+counts).after(ognizeEditHtml(counts));
	 $("#dianfei").val("");
	 $("#chanzhi").val("");
	 $("#start_time").val("");
	 initdate("temp_date_"+counts,date);
}
function ognizeShowHtml(){
	nengxiao = (chanzhi/dianfei).toFixed(2);
	var tip="<div class='head' id='record_"+counts+"'>" +
			"<span id='date_"+counts+"'>"+date+"</span>" +
			"<span id='dianfei_"+counts+"'>"+dianfei+"</span>" +
			"<span id='chanzhi_"+counts+"'>"+chanzhi+"</span>" +
			"<span id='nengxiao_"+counts+"' >"+nengxiao+"</span>"+
			"<span><a href='javascript:void(0);' class='small_button flip' onclick='editRecord("+counts+")'>编辑</a></span>"+
			"</div>"
	return tip;
}
//编辑
function editRecord(counts){
	$("#edit_"+counts).slideToggle(500);
}
function ognizeEditHtml(counts){
	var tip="<div class='panel' id='edit_"+counts+"'>"+
			"   <span  class='edit_number'><input  type='text' value='"+date+"'    id='temp_date_"+counts+"' />"+"</span>" +
			"	<span  class='edit_number'><input  type='text' value='"+dianfei+"' id='temp_dianfei_"+counts+"' /></span>"+
			"	<span  class='edit_number'><input  type='text' value='"+chanzhi+"' id='temp_chanzhi_"+counts+"' /></span>"+
			"	<span  class='edit_control'><a href='javascript:void(0);' class='small_button flip' onclick='saveRecord("+counts+")'>保存</a>&nbsp;<a href='javascript:void(0);' class='small_button' onclick='deleteRecord("+counts+")'>删除</a></span>"+
			"</div>";
	return tip;
}
//保存
function saveRecord(counts){
	dianfei = $("#temp_dianfei_"+counts).val();
	chanzhi = $("#temp_chanzhi_"+counts).val();
	date = $("#temp_date_"+counts).val();
	nengxiao = (chanzhi/dianfei).toFixed(2);
	$("#dianfei_"+counts).html(dianfei);
	$("#chanzhi_"+counts).html(chanzhi);
    $("#nengxiao_"+counts).html(nengxiao);
    $("#date_"+counts).html(date);
    $("#edit_"+counts).slideToggle(500);
}
function deleteRecord(counts){
	 $("#edit_"+counts).slideToggle(500);
	 $("#record_"+counts).fadeOut();
	 $("#record_"+counts).remove();
}

function initdate(id,date){
			var currYear = (new Date()).getFullYear();	
			var opt={};
			var minDate;
			opt.end = {
				theme: 'android-ics light', //皮肤样式
		        display: 'bottom', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
				dateFormat: 'yyyy',
        		dateOrder: 'yy'
			};
			opt.start = {
				theme: 'android-ics light', //皮肤样式
		        display: 'bottom', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
				dateFormat: 'yyyy',
        		dateOrder: 'yy'
			};
			opt.start_time = {preset : 'date',onSelect:function(valueText, inst){
			    opt.end.minDate = new Date(valueText.replace(/-/g,"/"));
			}};
			
			
			$("#"+id).val(date).scroller('destroy').scroller($.extend(opt['start_time'], opt['start']));
			
			
			opt.month = {preset : 'date',dateFormat:'yyyy-mm', dateOrder: 'yymm'};
			opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
			opt.datetime = {preset : 'datetime'};
			opt.time = {preset : 'time'};
			opt['default'] = {
				theme: 'android-ics light', //皮肤样式
		        display: 'bottom', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
		        startYear:currYear - 10, //开始年份
		        endYear:currYear + 10 //结束年份
			};
}
$(document).ready(function(){
  initdate("start_time");
  $("#addRecord").click(function(){
		addRecord();	  
  });
	
  $(".flip").click(function(){
   	 	$(".panel").slideToggle(500);
  });
});