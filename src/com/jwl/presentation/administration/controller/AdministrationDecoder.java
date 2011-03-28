package com.jwl.presentation.administration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.naming.NoPermissionException;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.presentation.article.controller.ArticleDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;

public class AdministrationDecoder extends ArticleDecoder {

	public AdministrationDecoder(Map<String, String> map,
			UIComponent component, IFacade facade) {
		super(map, component, facade);

	}

	@Override
	public void processDecode() throws ModelException, NoPermissionException {
		super.processDecode();
		if (isMergeIgnoreRequest()) {
			List<ArticleIdPair> idPairs = getIdPairs();
			facade.addToMergeSuggestionsIgnored(idPairs);
		}
		if (isDeadDeleteRequest()) {
			List<ArticleId> ids = getArticleIds();
			for (ArticleId id : ids) {
				facade.deleteArticle(id);
			}
		}

		if (isLivabilityIncreaseRequest()) {
			List<ArticleId> ids = getArticleIds();
			double increase = getLivabilityIncreaseValue();
			facade.increaseLivability(ids, increase);
		}

		if (isDeleteTopicRequest()) {
			handleDeleteTopic();
		}
		
		if(isCloseTopicRequest()){
			handleCloseTopic();
		}
		if(isOpenTopicRequest()){
			handleOpenTopic();
		}

	}

	private boolean isMergeIgnoreRequest() {
		return map.containsKey(JWLElements.KNOWLEDGE_IGNORE.id);
	}

	private boolean isDeadDeleteRequest() {
		return map.containsKey(JWLElements.KNOWLEDGE_DEAD_DELETE.id);
	}

	private boolean isLivabilityIncreaseRequest() {
		return map.containsKey(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY.id);
	}

	private boolean isDeleteTopicRequest() {
		return map.containsKey(JWLElements.FORUM_TOPIC_DELETE.id);
	}
	
	private boolean isCloseTopicRequest() {
		return map.containsKey(JWLElements.FORUM_TOPIC_CLOSE.id);
	}
	
	private boolean isOpenTopicRequest() {
		return map.containsKey(JWLElements.FORUM_TOPIC_OPEN.id);
	}

	private List<ArticleIdPair> getIdPairs() {
		List<ArticleIdPair> result = new ArrayList<ArticleIdPair>();
		for (Entry<String, String> e : map.entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id)) {
				ArticleIdPair pair = getPairFromCheckboxName(e.getKey());
				result.add(pair);
			}
		}
		return result;
	}

	private ArticleIdPair getPairFromCheckboxName(String checkboxName) {
		String pairPart = checkboxName
				.substring(JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id.length());
		int delimIdex = pairPart.indexOf('-');
		String firstId = pairPart.substring(0, delimIdex);
		String secondId = pairPart.substring(delimIdex + 1);
		int id1 = Integer.parseInt(firstId);
		int id2 = Integer.parseInt(secondId);
		return new ArticleIdPair(new ArticleId(id1), new ArticleId(id2));
	}

	private List<ArticleId> getArticleIds() {
		List<ArticleId> result = new ArrayList<ArticleId>();
		for (Entry<String, String> e : map.entrySet()) {
			if (e.getKey().contains(JWLElements.KNOWLEDGE_ID_CHECKBOX.id)) {
				ArticleId id = getIdFromCheckbox(e.getKey());
				result.add(id);
			}
		}
		return result;
	}

	private ArticleId getIdFromCheckbox(String chechboxName) {
		String idPart = chechboxName
				.substring(JWLElements.KNOWLEDGE_ID_CHECKBOX.id.length());
		int id = Integer.parseInt(idPart);
		return new ArticleId(id);
	}

	private double getLivabilityIncreaseValue()
			throws BreakBusinessRuleException {
		String value = map.get(JWLElements.KNOWLEDGE_LIVABILITY_INPUT.id);
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

	private void handleDeleteTopic() throws ModelException {
		List<Integer> topicIds = new ArrayList<Integer>();
		for (Entry<String, String> e : map.entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_TOPIC_CHBX.id)) {
				int topicId = getTopicIdFromCheckbox(e.getKey());
				topicIds.add(topicId);
			}
		}
		if(!topicIds.isEmpty()){
			facade.deleteForumTopics(topicIds);
		}
	}
	
	private void handleCloseTopic() throws ModelException {
		List<Integer> topicIds = new ArrayList<Integer>();
		for (Entry<String, String> e : map.entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_TOPIC_CHBX.id)) {
				int topicId = getTopicIdFromCheckbox(e.getKey());
				topicIds.add(topicId);
			}
		}
		if(!topicIds.isEmpty()){
			facade.closeForumTopics(topicIds);
		}
	}
	
	private void handleOpenTopic() throws ModelException {
		List<Integer> topicIds = new ArrayList<Integer>();
		for (Entry<String, String> e : map.entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_TOPIC_CHBX.id)) {
				int topicId = getTopicIdFromCheckbox(e.getKey());
				topicIds.add(topicId);
			}
		}
		if(!topicIds.isEmpty()){
			facade.openForumTopics(topicIds);
		}
	}

	private int getTopicIdFromCheckbox(String name) {
		String idPart = name.substring(JWLElements.FORUM_TOPIC_CHBX.id
				.length()+1);
		return Integer.parseInt(idPart);
	}

}
