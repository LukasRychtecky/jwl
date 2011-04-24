$('.jwl-ajax').livequery('click', function(event) {
	event.preventDefault();
	var thisTrigger = event.target;
	var href = $(this).attr('href');
	var questionMark = href.indexOf('?');
	var uri = href.substring(0, questionMark);
	if (questionMark > -1) {
		href = "&" + href.substring(questionMark + 1);
	}
		
	$.ajax(
	{
		url: "ajax.seam?jwlmethod=ajax&jwluri=" + uri + href,
		success: function(data) {
			$(thisTrigger).parents('.jwl-component').html(data);
		}
	});
});

$(document).ready(function() {
	$("div.jwl-flash-message:not(.jwl-no-hide)").livequery(function () {
		var message = $(this);
		setTimeout(function () {
			message.animate({"opacity": 0}, 1500);
			message.slideUp();
		}, 7000);
	});
	$('.markMe').markItUp(mySettings);
});


