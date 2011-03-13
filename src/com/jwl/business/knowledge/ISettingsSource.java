package com.jwl.business.knowledge;

import java.util.Map;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;

public interface ISettingsSource {
	public Map<String, WeightRecord> getWeights(String neuronName)throws KnowledgeManagementSettingsException;
	
	public float getTreshold(String neuronName)throws KnowledgeManagementSettingsException;
	
	public float getWeight(String neuronName, String inputName)throws KnowledgeManagementSettingsException;
	
	public String getCronExpression(String taskName) throws KnowledgeManagementSettingsException;
}
