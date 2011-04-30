package com.jwl.presentation.components.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.renderers.ACLPreview;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeDeadArticleList;
import com.jwl.presentation.renderers.EncodeDeadArticleView;
import com.jwl.presentation.renderers.EncodeMergeSuggestionList;
import com.jwl.presentation.renderers.EncodeMergeSuggestionView;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.renderers.units.FlashMessage.FlashMessageType;
import com.jwl.presentation.url.RequestMapDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministrationPresenter extends AbstractPresenter {

	public AdministrationPresenter() { }
	
	@Override
	public void renderDefault() {
		renderParams.put("uploadACLForm", this.createFormUploadACL());
		container.addAll(new EncodeAdministrationConsole(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public HtmlAppForm createFormUploadACL() {
		HtmlAppForm form = new HtmlAppForm("uploadACL");
		form.addFile("file", "File");
		form.addSubmit("submit", "Show preview", null);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.REDIRECT_TARGET, super.urlParser.getCurrentPage());
		params.put(JWLURLParams.STATE, "previewACL");
		params.put(JWLURLParams.DO, JWLActions.IMPORT_ACL.id);
		form.setAction(this.linker.buildLink(AbstractComponent.JWL_UPLOAD_FILE_PAGE, params));
		
		return form;
	}
	
	public void decodeUploadALC() {
		
	}
	
	public void decodeImportACL() {
		try {
			this.getFacade().importACL();
			FlashMessage message = new FlashMessage("Access Control List has been imported.");
			super.messages.add(message);
		} catch (ModelException ex) {
			super.defaultProcessException(ex, "default");
		}
	}
	
	public void renderPreviewACL() {
		try {
			renderParams.put("acl", super.getFacade().parseACL());
			container.addAll(new ACLPreview(linker, getFacade().getIdentity(), renderParams).render());		
		} catch (PermissionDeniedException ex) {			
			FlashMessage message = new FlashMessage(
					"You don't have a permission.",
					FlashMessage.FlashMessageType.ERROR, false);
			super.messages.add(message);
			this.redirect("default");
		} catch (ObjectNotFoundException e) {
			FlashMessage message = new FlashMessage(
					"No Access Control List found.",
					FlashMessage.FlashMessageType.WARNING, false);
			super.messages.add(message);
			this.renderDefault();
		} catch (ModelException ex) {
			super.defaultProcessException(ex, "default");
		}
	}
	
	public void renderMergeSuggestionList() throws ModelException {
		renderParams.put("articlePairs", this.getFacade().getMergeSuggestions());
		container.addAll(new EncodeMergeSuggestionList(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public void renderMergeSuggestionView() throws ModelException {
		container.addAll(new EncodeMergeSuggestionView(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public void renderDeadArticleList() throws ModelException {
		super.renderParams.put("deadArticles", this.getFacade().getDeadArticles());
		container.addAll(new EncodeDeadArticleList(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public void renderDeadArticleView() throws ModelException {
		container.addAll(new EncodeDeadArticleView(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public void renderKeyWordGeneration() throws ModelException{
		
	}
	
	public void decodeDeleteForumPost() throws ModelException {
		Integer postId = null;
		for(Entry<String, String> e: getRequestParamMap().entrySet()){
			if(e.getKey().contains(JWLElements.FORUM_TOPIC_ADMIN_FORM.id)){
				String id = e.getKey();
				String rest = id.substring(JWLElements.FORUM_TOPIC_ADMIN_FORM.id.length()+1);
				boolean isNumber = true;
				for(int i =0; i<rest.length(); i++){
					if(!Character.isDigit(rest.charAt(i))){
						isNumber=false;
						break;
					}
				}
				if(isNumber){
					postId = new Integer(rest);
					break;
				}
			}
		}
		if(postId!=null){		
			String elementKey = JWLElements.FORUM_TOPIC_ADMIN_FORM.id+"-"+postId+"-"+JWLElements.FORUM_TOPIC_DELETE.id;
			if(getRequestParamMap().containsKey(elementKey)){
				this.getFacade().deleteForumPost(postId);
			}
		
		}
		//renderAdminConsole();
	}
	
	
	public void decodeMergeIgnore() throws ModelException {
//		map.containsKey(decoder.getFullKey(JWLElements.KNOWLEDGE_IGNORE.id, JWLElements.KNOWLEDGE_MERGE_SUG_FORM.id));
		
		List<ArticleIdPair> idPairs = new ArrayList<ArticleIdPair>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id)) {
				
				int elementIdLength = JWLElements.KNOWLEDGE_MERGE_SUG_FORM.id.length()
						+ JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id.length() + 2;
				
				String pairPart = e.getKey().substring(elementIdLength);
				String[] split = pairPart.split("-");
				String firstId = split[0];
				String secondId = split[1];
				int id1 = Integer.parseInt(firstId);
				int id2 = Integer.parseInt(secondId);
				ArticleIdPair pair = new ArticleIdPair(new ArticleId(id1), new ArticleId(id2));
				idPairs.add(pair);
			}
		}
		this.getFacade().addToMergeSuggestionsIgnored(idPairs);
		
	}
	
	public void decodeDeadArticle() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.KNOWLEDGE_DEAD_SUG_FORM);
		
		List<ArticleId> articleIds = getArticleIds();
		if (decoder.containsKey(JWLElements.KNOWLEDGE_DEAD_DELETE)) {
			for (ArticleId id : articleIds) {
				this.getFacade().deleteArticle(id);
			}
		} else if (decoder.containsKey(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY)) {
			String value = decoder.getValue(JWLElements.KNOWLEDGE_LIVABILITY_INPUT);
			this.increaseLivability(articleIds, value);
		}
	}
	
	public void decodeKeyWordGeneration() throws ModelException{
		Environment.getKnowledgeFacade().extractKeyWords();
	}
	
	private List<ArticleId> getArticleIds() {
		int elementCheckBoxLength = JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id.length()
				+ AbstractComponent.JWL_HTML_ID_SEPARATOR.length()
				+ JWLElements.KNOWLEDGE_ID_CHECKBOX.id.length();
		
		List<ArticleId> result = new ArrayList<ArticleId>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_CHECKBOX.id)) {
				String idPart = e.getKey().substring(elementCheckBoxLength);
				ArticleId articleId = new ArticleId(Integer.parseInt(idPart));
				result.add(articleId);
			}
		}
		return result;
	}
	
	private void increaseLivability(List<ArticleId> articleIds, String value) throws ModelException {
		
		if(articleIds.isEmpty()) {
			messages.add(new FlashMessage("No article was selected.", FlashMessageType.ERROR));
			return;
		}
		
		Double increase = getLivabilityIncreaseValue(value);
		if (increase == null) {
			messages.add(new FlashMessage("Invalid livability value.", FlashMessageType.ERROR));
			return;
		} 			
		
		this.getFacade().increaseLivability(articleIds, increase);
	}

	private Double getLivabilityIncreaseValue(String value) {
		Double result = null;
		try {
			result = Double.parseDouble(value);
		} catch (Throwable t) {
			return null;
		}
		return result;
	}
}
