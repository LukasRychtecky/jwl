package com.jwl.presentation.component.enumerations;

public class JWLStyleClass {

	/** Prefix for determine html tags by jwl */
	public static final String JWL_PREFIX = "jwl-";
	/** Separator */
	public static final char HTML_ID_SEPARATOR = '-';
	
	public static final String NO_HIDE = JWL_PREFIX +							"no-hide";

	public static final String ACTION_BUTTON = JWL_PREFIX +						"action-button";
	public static final String ACTION_BUTTON_SMALLER = JWL_PREFIX +				"action-button-small";
	public static final String ACTION_LINK = JWL_PREFIX + 						"action-link";
	
	// // Main Page ////////////////
	public static final String CREATE_NEW_ARTICLE = JWL_PREFIX +				"createNewArticle";
	
	// // Table of Articles ////////
	public static final String TABLE_OF_ARTICLES = JWL_PREFIX +					"tableOfArticles";
	public static final String TABLE_HEADER_OF_ARTICLES = JWL_PREFIX +			"tableHearderOfArticles";
	public static final String LINK_NEXT_PAGE = JWL_PREFIX +					"nextPage";
	public static final String LINK_PREVIOUS_PAGE = JWL_PREFIX +				"previousPage";
	public static final String LINK_FIRST_PAGE = JWL_PREFIX +					"firstPage";
	public static final String LINK_LAST_PAGE = JWL_PREFIX +					"lastPage";

	// // Create/Edit State ////////
	public static final String EDIT_INPUT_SUBMIT = JWL_PREFIX + 				"edit-inputSubmit";
	// Labels
	public static final String EDIT_LABEL_OF_TITLE = JWL_PREFIX +				"edit-titleLabel";
	public static final String EDIT_LABEL_FOR_TITLE = JWL_PREFIX +				"edit-title-label";
	public static final String EDIT_LABEL_FOR_TEXT = JWL_PREFIX + 				"edit-text-label";
	public static final String EDIT_LABEL_FOR_TAGS = JWL_PREFIX + 				"edit-tags-label";
	public static final String EDIT_LABEL_FOR_CHANGE_NOTE = JWL_PREFIX + 		"edit-changeNote-label";
	// Title
	public static final String EDIT_TITLE = JWL_PREFIX + 						"edit-title";
	public static final String EDIT_TITLE_INPUT = JWL_PREFIX + 					"edit-title-input";
	// Inputs
	public static final String EDIT_TAGS_INPUT = JWL_PREFIX + 					"edit-tags-input";
	public static final String EDIT_CHANGE_NOTE_INPUT = JWL_PREFIX + 			"edit-changeNote-input";
	// Messages
	public static final String EDIT_MESSAGE_WARNING = JWL_PREFIX + 				"edit-warningMessage";

	// // Attach State ////////
	public static final String ATTACH_INPUT_SUBMIT = JWL_PREFIX + 				"upload-inputSubmit";
	// Labels
	public static final String ATTACH_LABEL_FOR_FILE_NAME = JWL_PREFIX + 		"upload-fileName-label";
	public static final String ATTACH_LABEL_FOR_FILE = JWL_PREFIX + 			"upload-file-label";
	public static final String ATTACH_LABEL_FOR_FILE_DESCRIPTION = JWL_PREFIX + "upload-fileDescription-label";
	// Inputs
	public static final String ATTACH_FILE_NAME = JWL_PREFIX + 					"upload-fileName-input";
	public static final String ATTACH_FILE = JWL_PREFIX + 						"upload-file-input";
	public static final String ATTACH_FILE_DESCRIPTION = JWL_PREFIX + 			"upload-fileDescription-input";
	
	// // View State //////////
	public static final String VIEW_TITLE = JWL_PREFIX + 						"view-title";
	public static final String VIEW_TEXT = JWL_PREFIX + 						"view-text";
	public static final String VIEW_TAGS = JWL_PREFIX + 						"view-tags";
	public static final String VIEW_LINK_BACK = JWL_PREFIX + 					"view-link-back";
	public static final String VIEW_LINK_EDIT = JWL_PREFIX + 					"view-link-edit";
	public static final String VIEW_LINK_ATTACH = JWL_PREFIX + 					"view-link-attach";
	
	
	// // Search //////////////
	public static final String SEARCH_INPUT_SUBMIT = JWL_PREFIX + 				"search-inputSubmit";
	public static final String SEARCH_INPUT = JWL_PREFIX + 						"search-input";
	
	// // Messagges
	public static final String FLASH_PREFIX = JWL_PREFIX +						"flash-";
	public static final String FLASH_MESSAGE = FLASH_PREFIX +					"message";
	
	public static final String VIEW_STARS = JWL_PREFIX + "stars";
	public static final String PAGE_BUTTON = JWL_PREFIX + "pageButton";
	public static final String PAGE_TEXT = JWL_PREFIX + "pageText";
	public static final String PAGE_BUTTONS_TABLE = JWL_PREFIX + "pageTable";
	public static final String PANEL = JWL_PREFIX + "panel";
	public static final String PANEL_HEADER = JWL_PREFIX + "panelHeader";
	public static final String PANEL_BODY = JWL_PREFIX + "panelBody";
	public static final String PANEL_ACTION_BUTTONS = JWL_PREFIX + "panelActionButtons";
	
}
