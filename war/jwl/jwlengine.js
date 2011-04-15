$(document).ready(function() {
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
				$(thisTrigger).parent('.jwl-component').html(data);
			}
		});
	});
});


