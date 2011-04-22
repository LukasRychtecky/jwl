package com.jwl.presentation.renderers.units;

import com.jwl.business.article.ArticleId;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;

public class RatingComponent {

	public static HtmlDiv getComponent(float rating) {
		int sn = (int) rating;
		int r = (int) (rating * 10) % 1;
		if (r >= 5) {
			sn++;
		}

		HtmlDiv stars = new HtmlDiv();
		stars.setStyleClass("smallstars");
		for (int i = 0; i < 10; i++) {
			HtmlDiv star = new HtmlDiv();
			if (i < sn) {
				star.setStyleClass("rating");
			}
			if (i % 2 == 1) {
				star.setStyleClass("rating-right");
			}
			stars.addChildren(star);
		}
		return stars;
	}

	public static HtmlFreeOutput encodedRating(float ratingAverage, ArticleId articleId) {
		StringBuilder builder = new StringBuilder();
		
		int sn = (int) ratingAverage;
		int r = (int) (ratingAverage % 1) * 10;
		if (r >= 5) {
			sn++;
		}

		builder.append("<form action=\"\">");
		builder.append("<div class=\""+JWLStyleClass.VIEW_STARS+"\">");
		for (int i = 1; i < 11; i++) {
			float sv = (float) i / 2;
			if (i == sn) {
				builder.append(encodedStar(i, sv, true));
			} else {
				builder.append(encodedStar(i, sv, false));
			}
		}
		builder.append("</div>");
		
		builder.append("<input id=\"rating-article-id\" type=\"hidden\" value=\""
			+ articleId.getId().intValue() + "\" />");
		builder.append("</form>");
		
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(builder.toString());
		return output;
	}

	private static String encodedStar(int elementNumber, float starValue, boolean checked) {
		StringBuilder builder = new StringBuilder();
		builder.append("<label for=\"rating-" + elementNumber + "\">");
		builder.append("<input id=\"rating-" + elementNumber
				+ "\" name=\"rating\" type=\"radio\" value=\"" + elementNumber
				+ "\"");
		if (checked) {
			builder.append(" checked");
		}
		builder.append("\"/>");
		builder.append(starValue + " stars");
		builder.append("</label>");
		return builder.toString();
	}
}
