package com.jwl.presentation.article.renderer;

import com.jwl.presentation.article.controller.ArticleController;
import com.jwl.presentation.component.renderer.JWLRenderer;

/**
 * 
 * @author Petr Dytrych
 * @review Jiri Ostatnicky
 * @review Lukas Rychtecky
 * @review Petr Dytrych
 */
public class ArticleRenderer extends JWLRenderer {

	public ArticleRenderer() {
		this.controller = new ArticleController();
	}
}
