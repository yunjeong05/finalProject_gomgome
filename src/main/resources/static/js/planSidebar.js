$(function(){
	$('.feat-btn').click(function(){
      $('nav ul .feat-show').toggleClass("show");
      $('nav ul .first').toggleClass("rotate");
    });
    $('nav ul li').click(function(){
      // console.log($(this));
      $(this).addClass("active").siblings().removeClass("active");
      if($(this).siblings().attr("class", "")){
        $(this).siblings().children().children().removeClass("active");
      }
    });
});