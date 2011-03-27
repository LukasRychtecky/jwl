package com.jwl.business.knowledge.util;

import java.util.Map;
import java.util.Map.Entry;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;

public class Neuron {
	private ISettingsSource weightSource;
	private String neuronName;

	public Neuron(ISettingsSource weightSource,
			String neuronName) {
		this.weightSource = weightSource;
		this.neuronName = neuronName;
	}

	public float getOutput(Map<String,WeightRecord> input) {
		float inputSum = sumInputs(input);
		float treshold;
		try {
			treshold = weightSource.getThreshold(neuronName);
		} catch (KnowledgeManagementSettingsException e) {
			return 0;
		}
		if (inputSum >= treshold) {
			return inputSum;
		}
		return 0;
	}

	private float sumInputs(Map<String,WeightRecord> input) {
		float result = 0;
		for (Entry<String,WeightRecord> in : input.entrySet()) {
			try {
				result += in.getValue().getWeight()
						* weightSource.getWeight(neuronName, in.getKey());
			} catch (KnowledgeManagementSettingsException e) {
				return 0;
			} 
		}
		return result;
	}
}
