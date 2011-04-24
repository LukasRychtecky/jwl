package com.jwl.presentation.components.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.jwl.business.ArticlePair;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeDeadArticleList;
import com.jwl.presentation.renderers.EncodeDeadArticleView;
import com.jwl.presentation.renderers.EncodeMergeSuggestionList;
import com.jwl.presentation.renderers.EncodeMergeSuggestionView;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.renderers.units.FlashMessage.FlashMessageType;
import com.jwl.presentation.url.RequestMapDecoder;

public class AdministrationPresenter extends AbstractPresenter {

	public AdministrationPresenter() { }
	
	@Override
	public void renderDefault() {
		container.addAll(new EncodeAdministrationConsole().getEncodedComponent());
	}
	
	public void renderAdminConsole() {
		container.addAll(new EncodeAdministrationConsole().getEncodedComponent());
	}
	
	public void renderMergeSuggestionList() throws ModelException {
		List<ArticlePair> mergeSuggestions = this.getFacade().getMergeSuggestions();
		container.addAll(new EncodeMergeSuggestionList(mergeSuggestions).getEncodedComponent());
	}
	
	public void renderMergeSuggestionView() throws ModelException {
		container.addAll(new EncodeMergeSuggestionView().getEncodedComponent());
	}
	
	public void renderDeadArticleList() throws ModelException {
		List<ArticleTO> deadArticles = this.getFacade().getDeadArticles();
		container.addAll(new EncodeDeadArticleList(deadArticles).getEncodedComponent());
	}
	
	public void renderDeadArticleView() throws ModelException {
		container.addAll(new EncodeDeadArticleView().getEncodedComponent());
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
		renderAdminConsole();
	}
	
	
	public void decodeMergeIgnoreRequest() throws ModelException {
//		map.containsKey(decoder.getFullKey(JWLElements.KNOWLEDGE_IGNORE.id, JWLElements.KNOWLEDGE_MERGE_SUG_FORM.id));
		
		List<ArticleIdPair> idPairs = new ArrayList<ArticleIdPair>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id)) {
				
				int elementIdLength = JWLElements.KNOWLEDGE_MERGE_SUG_FORM.id.length()
						+ JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id.length() + 1;
				
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
		
		renderAdminConsole();
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
