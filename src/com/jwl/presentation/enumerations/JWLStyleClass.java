package com.jwl.presentation.enumerations;

public class JWLStyleClass {

	/** Prefix for determine html tags by jwl */
	public static final String JWL_PREFIX = "jwl-";
	
	public static final String NO_HIDE = JWL_PREFIX +							"no-hide";
	// Inputs
	public static final String ATTACH_FILE_NAME = JWL_PREFIX + 					"upload-fileName-input";
	public static final String ATTACH_FILE = JWL_PREFIX + 						"upload-file-input";
	public static final String ATTACH_FILE_DESCRIPTION = JWL_PREFIX + 			"upload-fileDescription-input";
	
	// // View State //////////
	public static final String VIEW_TITLE = JWL_PREFIX + 						"view-title";
	public static final String VIEW_TEXT = JWL_PREFIX + 						"view-text";
	public static final String VIEW_TAGS = JWL_PREFIX + 						"view-tags";
	public static final String VIEW_ATTACHMENTS = JWL_PREFIX + 					"view-attachments";
	public static final String VIEW_LINK_BACK = JWL_PREFIX + 					"view-link-back";
	public static final String VIEW_LINK_EDIT = JWL_PREFIX + 					"view-link-edit";
	public static final String VIEW_LINK_ATTACH = JWL_PREFIX + 					"view-link-attach";
	public static final String VIEW_STARS = JWL_PREFIX + 						"view-stars";
	
	// // Search //////////////
	public static final String SEARCH_INPUT_SUBMIT = JWL_PREFIX + 				"search-inputSubmit";
	public static final String SEARCH_INPUT = JWL_PREFIX + 						"search-input";
	
	// // Messagges
	public static final String FLASH_PREFIX = JWL_PREFIX +						"flash-";
	public static final String FLASH_MESSAGE = FLASH_PREFIX +					"message";
	
	public static final String FORUM_PAGE_BUTTON = JWL_PREFIX + 				"forum-pageButton";
	public static final String FORUM_PAGE_TEXT = JWL_PREFIX + 					"forum-pageText";
	public static final String FORUM_PAGE_BUTTONS_TABLE = JWL_PREFIX + 			"forum-pageTable";
	
	public static final String PANEL = JWL_PREFIX + 							"panel";
	public static final String PANEL_HEADER = JWL_PREFIX + 						"panelHeader";
	public static final String PANEL_BODY = JWL_PREFIX + 						"panelBody";
	public static final String PANEL_ACTION_BUTTONS = JWL_PREFIX + 				"panelActionButtons";
	
	public static final String FORUM_POST_HEADER = JWL_PREFIX + 				"forum-postHeader";
	public static final String FORUM_POST_HEADER_TITLE = JWL_PREFIX + 			"forum-postHeaderTitle";
	public static final String FORUM_POST_AUTHOR = JWL_PREFIX + 				"forum-postAuthor";
	public static final String FORUM_INITIAL_POST = JWL_PREFIX + 				"forum-post"; 
	public static final String FORUM_REPLIES = JWL_PREFIX + 					"forum-replies";
	public static final String FORUM_REPLY_ACTIONS = JWL_PREFIX + 				"forum-replyActions";
	public static final String FORUM_ACTIONS_FORM = JWL_PREFIX + 				"forum-actionsForm";
	public static final String FORUM_REPLIES_DELETE = JWL_PREFIX + 				"forum-replies-delete";
	
	//adds markdown editor
	public static final String MARK_ME = "markMe";
	
	//stars
	public static final String STARS_SMALL = JWL_PREFIX + "smallstars";
	public static final String STARS_RATING = JWL_PREFIX + "rating";
	public static final String STARS_RATING_RIGHT = JWL_PREFIX + "rating-right";
	public static final String STARS_RATING_DIV = JWL_PREFIX + "rating-div";
	
	//similar article suggestor
	public static final String SUGGESTOR_DIV = JWL_PREFIX + "suggestor";
	public static final String SUGGESTOR_DIV_VIEW = JWL_PREFIX + "suggestor-view";
}
