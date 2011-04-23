$(document).ready(function(){
    $("#jwl-edit-title").blur(function(){
        suggestSimilarArticle();
    });
    $("#jwl-edit-tags").blur(function(){
        suggestSimilarArticle();
    });
    $("#jwl-edit-text").blur(function(){
        suggestSimilarArticle();
    });
    $('#jwl-edit-text').keyup(function(e){
        if (e.keyCode == 13) {
            suggestSimilarArticle();
        }
    });
    $("#jwl-admin-kn-kw").click(function(){
        $.ajax({
            type: "POST",
            url: "/SeamWiki/KnowledgeAjax/KeyWordExtraction",
        });
    });
});
function suggestSimilarArticle(){
    $.ajax({
        type: "POST",
        url: "/SeamWiki/KnowledgeAjax/SimilarArticleServlet",
        data: "title=" + $("#jwl-edit-title").val() + "&tags=" + $("#jwl-edit-tags").val() + "&text=" + $("#jwl-edit-text").val(),
        success: function(data){
            $("#sad").html(data);
        },
    });
};

