$(document).ready(function(){
    starRating.create('.jwl-view-stars');
});

// The widget
var starRating = {
    create: function(selector){
        // loop over every element matching the selector
        $(selector).each(function(){
            var $list = $('<div></div>');
            // loop over every radio button in each container
            $(this).find('input:radio').each(function(i){
                var rating = $(this).parent().text();
                var $item = $('<a href="#"></a>').attr('title', rating).addClass(i % 2 == 1 ? 'rating-right' : '').text(rating);
                
                starRating.addHandlers($item);
                $list.append($item);
                
                if ($(this).is(':checked')) {
                    $item.prevAll().andSelf().addClass('rating');
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
            $allLinks.children().removeClass('rating');
            $star.prevAll().andSelf().addClass('rating');
            starRating.rated = null;
            
            // prevent default link click
            e.preventDefault();
            var sendRating = function(){
                var checkedVal = $allLinks.parent().find('input:radio:checked').val();
				var articeId = $allLinks.parent().parent().find('input#rating-article-id').val();
                $.ajax({
                    type: "POST",
                    url: "/SeamWiki/KnowledgeAjax/RatingServlet",
                    data: "ratedValue=" + checkedVal+"&articleId="+articeId,
                    success: function(data){
                        //add new rating
                        var j = $allLinks.parent().find('input:radio[value=' + data + ']');
                        $allLinks.parent().find('input:radio[value=' + data + ']').attr('checked', true);
                        
						//remove stars
                        $allLinks.children().removeClass('rating');
						//add new rating average
                        var ratingNum = parseInt(data);
                        $($allLinks.children().get(ratingNum - 1)).prevAll().andSelf().addClass('rating');
                    },
                });
            }
            sendRating();
            
        }).hover(function(){
            // Handle star mouse over
            var $allLinks = $(this).parent();
            starRating.rated = $("a.rating");
            starRating.rated.removeClass('rating');
            $(this).prevAll().andSelf().addClass('rating-over');
        }, function(){
            // Handle star mouse out
            $(this).siblings().andSelf().removeClass('rating-over');
            starRating.rated.addClass('rating');
        });
    }
    
}
