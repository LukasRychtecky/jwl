package com.jwl.presentation.component.enumerations;

import java.util.ArrayList;
import java.util.List;

public class JWLURLParameters {

	public static final String ARTICLE_TITLE = "jwltitle";
	public static final String ACTION = "jwlaction";
	public static final String FILE_NAME = "jwlfilename";
	public static final String ARTICLE_ID = "jwlid";
	public static final String REDIRECT_TARGET = "jwlpage";
	public static final String LIST_PAGE_NUMBER = "jwlpageindex";
	public static final String LIST_ORDER_COLUMN = "jwlordercolumn";
	public static final String HISTORY_ID = "historyid";
	public static final String FILE_ACTION = "jwlfileaction";
	
	public static List<String> ALL_URL_PARAMETERS;
	public static List<String> ALL_URL_PARAMETERS_BUT_TITLE;
	
	static{
		ALL_URL_PARAMETERS = new ArrayList<String>();
		ALL_URL_PARAMETERS.add(ARTICLE_TITLE);
		ALL_URL_PARAMETERS.add(ACTION);
		ALL_URL_PARAMETERS.add(FILE_NAME);
		ALL_URL_PARAMETERS.add(ARTICLE_ID);
		ALL_URL_PARAMETERS.add(REDIRECT_TARGET);
	//	ALL_URL_PARAMETERS.add(LIST_PAGE_NUMBER);
		
		ALL_URL_PARAMETERS_BUT_TITLE = new ArrayList<String>();
		ALL_URL_PARAMETERS_BUT_TITLE.add(ACTION);
		ALL_URL_PARAMETERS_BUT_TITLE.add(FILE_NAME);
		ALL_URL_PARAMETERS_BUT_TITLE.add(ARTICLE_ID);
		ALL_URL_PARAMETERS_BUT_TITLE.add(REDIRECT_TARGET);
	//	ALL_URL_PARAMETERS_BUT_TITLE.add(LIST_PAGE_NUMBER);
	}

}
