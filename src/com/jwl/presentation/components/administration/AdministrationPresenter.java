package com.jwl.presentation.components.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.jwl.business.ArticlePair;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.presentation.components.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeDeadArticleList;
import com.jwl.presentation.renderers.EncodeDeadArticleView;
import com.jwl.presentation.renderers.EncodeMergeSuggestionList;
import com.jwl.presentation.renderers.EncodeMergeSuggestionView;
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
	
	
	
	public void decodeDeadDeleteRequest() throws ModelException {
//		map.containsKey(decoder.getFullKey(JWLElements.KNOWLEDGE_DEAD_DELETE.id, JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id));
		for (ArticleId id : getArticleIds()) {
			this.getFacade().deleteArticle(id);
		}
		renderAdminConsole();
	}
	
	public void decodeLivabilityIncreaseRequest() throws ModelException {
//		map.containsKey(decoder.getFullKey(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY.id, JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id))
		List<ArticleId> ids = getArticleIds();
		double increase = getLivabilityIncreaseValue();
		this.getFacade().increaseLivability(ids, increase);
		renderAdminConsole();
	}
	
	private List<ArticleId> getArticleIds() {
		List<ArticleId> result = new ArrayList<ArticleId>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_CHECKBOX.id)) {
				
				int elementIdLength = JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id.length()
					+ JWLElements.KNOWLEDGE_ID_CHECKBOX.id.length() + 1;
				
				String idPart = e.getKey().substring(elementIdLength);
				int id = Integer.parseInt(idPart);
				ArticleId articleId = new ArticleId(id);
				result.add(articleId);
			}
		}
		return result;
	}
	
	private double getLivabilityIncreaseValue() throws BreakBusinessRuleException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.KNOWLEDGE_DEAD_SUG_FORM);
		String value = decoder.getValue(JWLElements.KNOWLEDGE_LIVABILITY_INPUT);
		if (value == null || value == "") {
			throw new BreakBusinessRuleException("Livability must be filled");
		}
		double result = 0;
		try {
			result = Double.parseDouble(value);
		} catch (Throwable t) {
			throw new BreakBusinessRuleException(
					"Livability increase must be a number.");
		}
		return result;
	}
}
