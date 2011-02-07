package com.jwl.presentation.search.renderer;

import com.jwl.presentation.component.renderer.JWLRenderer;
import com.jwl.presentation.search.controller.SearchController;

public class SearchRenderer extends JWLRenderer {

	public SearchRenderer (){
		this.controller = new SearchController();
	}

}
