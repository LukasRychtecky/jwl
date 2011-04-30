package com.jwl.business.knowledge.util;

import java.util.Map;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;

public interface ISettingsSource {
	public Map<String, WeightRecord> getWeights(String neuronName)throws KnowledgeManagementSettingsException;
	
	public float getThreshold(String neuronName)throws KnowledgeManagementSettingsException;
	
	public float getWeight(String neuronName, String inputName)throws KnowledgeManagementSettingsException;
	
	public String getCronExpression(String taskName) throws KnowledgeManagementSettingsException;
	
	public String getWordCountsFile();
	
	public String getMergeFile();
	
	public String getMergeIgnoreFile();
}
