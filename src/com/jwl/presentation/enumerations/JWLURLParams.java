package com.jwl.presentation.enumerations;

import java.util.ArrayList;
import java.util.List;

public class JWLURLParams {

	public static final String DO = "jwldo";
	public static final String URI = "jwluri";
	public static final String METHOD = "jwlmethod";
	public static final String PRESENTER = "jwlpresenter";
	public static final String ARTICLE_TITLE = "jwltitle";
	public static final String STATE = "jwlstate";
	public static final String FILE_NAME = "jwlfilename";
	public static final String REDIRECT_TARGET = "jwlpage";
	public static final String LIST_PAGE_NUMBER = "jwlpageindex";
	public static final String LIST_ORDER_COLUMN = "jwlordercolumn";
	public static final String HISTORY_ID = "jwlhistoryid";
	public static final String FILE_ACTION = "jwlfileaction";
	public static final String TOPIC_ID = "topicid";
	public static final String ANSWERING = "answering";
	public static final String QUOTE_POST_ID = "quotepostid";
	
	public static List<String> ALL_URL_PARAMETERS;
	public static List<String> ALL_URL_PARAMETERS_BUT_TITLE;
	
	static{
		ALL_URL_PARAMETERS = new ArrayList<String>();
		ALL_URL_PARAMETERS.add(DO);
		ALL_URL_PARAMETERS.add(URI);
		ALL_URL_PARAMETERS.add(METHOD);
		ALL_URL_PARAMETERS.add(PRESENTER);
		ALL_URL_PARAMETERS.add(ARTICLE_TITLE);
		ALL_URL_PARAMETERS.add(STATE);
		ALL_URL_PARAMETERS.add(FILE_NAME);
		ALL_URL_PARAMETERS.add(REDIRECT_TARGET);
		ALL_URL_PARAMETERS.add(LIST_PAGE_NUMBER);
		ALL_URL_PARAMETERS.add(LIST_ORDER_COLUMN);
		ALL_URL_PARAMETERS.add(HISTORY_ID);
		ALL_URL_PARAMETERS.add(FILE_ACTION);
		ALL_URL_PARAMETERS.add(TOPIC_ID);
		ALL_URL_PARAMETERS.add(ANSWERING);
		ALL_URL_PARAMETERS.add(QUOTE_POST_ID);
		
		ALL_URL_PARAMETERS_BUT_TITLE = new ArrayList<String>();
		ALL_URL_PARAMETERS_BUT_TITLE.add(DO);
		ALL_URL_PARAMETERS_BUT_TITLE.add(URI);
		ALL_URL_PARAMETERS_BUT_TITLE.add(METHOD);
		ALL_URL_PARAMETERS_BUT_TITLE.add(PRESENTER);
		ALL_URL_PARAMETERS_BUT_TITLE.add(ARTICLE_TITLE);
		ALL_URL_PARAMETERS_BUT_TITLE.add(STATE);
		ALL_URL_PARAMETERS_BUT_TITLE.add(FILE_NAME);
		ALL_URL_PARAMETERS_BUT_TITLE.add(REDIRECT_TARGET);
		ALL_URL_PARAMETERS_BUT_TITLE.add(LIST_PAGE_NUMBER);
		ALL_URL_PARAMETERS_BUT_TITLE.add(LIST_ORDER_COLUMN);
		ALL_URL_PARAMETERS_BUT_TITLE.add(HISTORY_ID);
		ALL_URL_PARAMETERS_BUT_TITLE.add(FILE_ACTION);
		ALL_URL_PARAMETERS_BUT_TITLE.add(TOPIC_ID);
		ALL_URL_PARAMETERS_BUT_TITLE.add(ANSWERING);
		ALL_URL_PARAMETERS_BUT_TITLE.add(QUOTE_POST_ID);
	}

}
