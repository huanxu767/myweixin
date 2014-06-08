Zepto(function($){
  /* load start */
  addEventListener('load', function(){ setTimeout(function(){
      window.scrollTo(0,1);}, 0); 
      //棣栭〉骞荤伅
      var inSlider=$('#J_inSlider');
      var indexMenuShort=$('.index-menu .short img');
      var indexMenuUpH=$('.index-menu li,.index-menu .long img');
      //瑙嗙獥鏀瑰彉
      if(inSlider.size()>0){
          inSlider.height(inSlider.width()/2);
          inSlider.slider();
      }
      //鏇存柊鑿滃崟楂樺害
      var indexShortH=indexMenuShort.height();
      indexMenuUpH.height(indexShortH);

  }, false);
  //閫氱敤瀵艰埅鏄剧ず闅愯棌
  var baseNav=$('.plug-menu');
  baseNav.click(function(){
    var span = $(this).find('span');
    if(span.attr('class') == 'open'){
            span.removeClass('open');
            span.addClass('close');
            $('.plug-btn').removeClass('open');
            $('.plug-btn').addClass('close');
    }else{
            span.removeClass('close');
            span.addClass('open');
            $('.plug-btn').removeClass('close');
            $('.plug-btn').addClass('open');
    }
  });
  baseNav.on('touchmove',function(event){event.preventDefault();});
  //椤圭洰璇︽儏椤碉紝灞曞紑鏇村
  var projecDMore=$('.superpage a.more');
 
  projecDMore.click(function(){
	  
	  //if($(this).attr('key')==null){return;}
	  var projectDIntro=$('#J_detailContent'+$(this).attr('key')+' .intro');
      var projectDTxt=$('#J_detailContent'+$(this).attr('key')+' .txt');
      
	  if($(this).hasClass('more-click')){
      
      $(this).removeClass('more-click').html('鏇村<span></span>');
      projectDIntro.show();
      projectDTxt.hide();
    }else{
      $(this).addClass('more-click').html('鏀惰捣<span></span>');
      projectDIntro.hide();
      projectDTxt.show();
    }
  });
  

  /* load end */
});