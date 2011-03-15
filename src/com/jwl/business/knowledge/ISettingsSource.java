package com.jwl.business.knowledge;

import java.util.Map;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsExceptiuon;

public interface ISettingsSource {
	public Map<String, WeightRecord> getWeights(String neuronName)throws KnowledgeManagementSettingsExceptiuon;
	public float getTreshold(String neuronName)throws KnowledgeManagementSettingsExceptiuon;
	public float getWeight(String neuronName, String inputName)throws KnowledgeManagementSettingsExceptiuon;
}
