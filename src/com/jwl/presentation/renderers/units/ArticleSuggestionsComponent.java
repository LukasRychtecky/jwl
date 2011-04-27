package com.jwl.presentation.renderers.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;

public class ArticleSuggestionsComponent {

	public static List<UIComponent> getComponent(
			List<ArticleTO> similarArticles, FacesContext context){
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(getSimilarArticlesComponent(similarArticles, context));
		return components;
	}

	private static UIComponent getSimilarArticlesComponent(
			List<ArticleTO> similarArticles, FacesContext context){
		HtmlDiv head = new HtmlDiv();
		head.setText("Similar articles:");
		HtmlFreeOutput component = new HtmlFreeOutput();
		component.getChildren().add(head);
		component.getChildren().add(
				getTableOfArticles(similarArticles, context));
		return component;
	}

	private static UIComponent getTableOfArticles(List<ArticleTO> articles,
			FacesContext context){
		UIComponent table = getTable();
		List<UIComponent> tableChildren = table.getChildren();
		for (ArticleTO article : articles){
			tableChildren.add(encodeArticleLink(article.getTitle(), context));
		}
		return table;
	}

	private static UIComponent getTable(){
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(1);
		table.setCellpadding("0");
		table.setCellspacing("0");
		return table;
	}

	private static UIComponent encodeArticleLink(String title,
			FacesContext context){
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		if(title != null){
			params.put(JWLURLParams.ARTICLE_TITLE, title);
		}
		HtmlLink link = new HtmlLink();
		Linker linker = (Linker) context.getAttributes().get(
				JWLContextKey.LINKER);
		link.setValue(linker.buildLink(params));
		link.setText(title);
		link.setIsAjax(true);
		return link;
	}
}
