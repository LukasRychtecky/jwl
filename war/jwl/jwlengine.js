var JWL = {
	init: function() {		
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
					JWL.markIt();
					starRating.create('.jwl-view-stars');
					knowledgeAjax.init();
				}
			});
		});
		
		$("div.jwl-flash-message:not(.jwl-no-hide)").livequery(function () {
			var message = $(this);
			setTimeout(function () {
				message.animate({"opacity": 0}, 1500);
				message.slideUp();
			}, 7000);
		});
		this.markIt();
		starRating.create('.jwl-view-stars');
		knowledgeAjax.init();
	},
	markIt: function() {
		$('.markMe').markItUp(mySettings);
	},
	forms: {},
	validateForm: function(form) {
		var validators = this.forms[form.id];
		
		if (validators === null) {
			return false;
		}
		var result = null;
		var $input = null;
		
		for (var i in validators) {
			$input = $('#' + validators[i].name);
			result = validators[i].validate($input.val());
			var $message = $input.parents().children('.ui-state-error.ui-corner-all');
			if (result != null) {
				if ($message === null || $message.length === 0) {    
					$message = $('<div>', {
						'class': 'ui-state-error ui-corner-all',
						'html': $('<p>', {
							'text': result
						}).prepend($('<span>', {
								'class': 'ui-icon ui-icon-alert'
							}))
					});

					$message.prependTo($input.parent());
				}				
				$input.focus();
				return false;
			} else {
				if ($message !== null) {
					$message.remove();
				}
			}
		}
		return true;
	}
};
$(document).ready(function() {
	JWL.init();
});

var knowledgeAjax = {
	suggestSimilarArticle : function(){
		$.ajax({
        type: "POST",
        url: "ajax.seam?jwlmethod=ajax&jwlstate=articleSuggestions&jwlpresenter=Article",
        data: "jwltitle=" + $("#jwl-title").val() + "&jwltags=" + $("#jwl-tags").val() + "&jwltext=" + $("#jwl-text").val(),
        success: function(data){
            $(".jwl-suggestor").html(data);
        }
    });
	},
	
	init : function(){
		$("#jwl-title").blur(function(){
        knowledgeAjax.suggestSimilarArticle();
    });
    $("#jwl-tags").blur(function(){
        knowledgeAjax.suggestSimilarArticle();
    });
    $("#jwl-text").blur(function(){
        knowledgeAjax.suggestSimilarArticle();
    });
    $('#jwl-text').keyup(function(e){
        if (e.keyCode == 13) {
            knowledgeAjax.suggestSimilarArticle();
        }
    });
    $("#jwl-admin-kn-kw").click(function(){
        $.ajax({
            type: "POST",
            url: "ajax.seam?jwlmethod=ajax&jwldo=keyWordGeneration&jwlstate=keyWordGeneration&jwlpresenter=Administration"
        });
    });
	}		
}

// The widget
var starRating = {
    create: function(selector){
        // loop over every element matching the selector
        $(selector).each(function(){
            var $list = $('<div></div>');
            // loop over every radio button in each container
            $(this).find('input:radio').each(function(i){
                var rating = $(this).parent().text();
                var $item = $('<a href="#"></a>').attr('title', rating).addClass(i % 2 == 1 ? 'jwl-rating-right' : '').text(rating);
                
                starRating.addHandlers($item);
                $list.append($item);
                
                if ($(this).is(':checked')) {
                    $item.prevAll().andSelf().addClass('jwl-rating');
                }
            });
            // Hide the original radio buttons
            $(this).append($list).find('label').hide();
        });
    },
    addHandlers: function(item){
        $(item).click(function(e){
            // Handle Star click
            var $star = $(this);
            var $allLinks = $(this).parent();
            
            // Set the radio button value
            $allLinks.parent().find('input:radio[value=' + $star.text() + ']').attr('checked', true);
            
            $allLinks.parent().find('input:radio').each(function(a){
                var t = $(this).parent().text();
                if (t == $star.text()) {
                    $(this).attr('checked', true);
                }
            });
            
            // Set the ratings
            $allLinks.children().removeClass('jwl-rating');
            $star.prevAll().andSelf().addClass('jwl-rating');
            starRating.rated = null;
            
            // prevent default link click
            e.preventDefault();
            var sendRating = function(){
                var checkedVal = $allLinks.parent().find('input:radio:checked').val();
				var articeId = $allLinks.parent().parent().find('input#jwl-rating-article-id').val();
                $.ajax({
                    type: "POST",
                    url: "ajax.seam?&jwlmethod=ajax&jwlstate=rating&jwlpresenter=Article",
                    data: "jwlratedValue=" + checkedVal+"&jwlarticleId="+articeId,
                    success: function(data){
                        $(".jwl-rating-div").html(data);
						starRating.create('.jwl-view-stars');
                    }
                });
            }
            sendRating();
            
        }).hover(function(){
            // Handle star mouse over
            var $allLinks = $(this).parent();
            starRating.rated = $("a.jwl-rating");
            starRating.rated.removeClass('jwl-rating');
            $(this).prevAll().andSelf().addClass('jwl-rating-over');
        }, function(){
            // Handle star mouse out
            $(this).siblings().andSelf().removeClass('jwl-rating-over');
            starRating.rated.addClass('jwl-rating');
        });
    }
    
}
