$(document).ready(function() {
	$('#ajax').click(function() {
		$.ajax(
		{
			url:"/faces/ajax-listener?neco=bla",
			success: function() {
				alert('response');
			}
		});
	});
});


