package com.jwl.business.knowledge;

import java.util.Map.Entry;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsExceptiuon;

public class Neuron {
	private INeuronInput input;
	private ISettingsSource weightSource;
	private String neuronName;

	public Neuron(INeuronInput input, ISettingsSource weightSource,
			String neuronName) {
		this.input = input;
		this.weightSource = weightSource;
		this.neuronName = neuronName;
	}

	public float getOutput() {
		float inputSum = sumInputs();
		float treshold;
		try {
			treshold = weightSource.getTreshold(neuronName);
		} catch (KnowledgeManagementSettingsExceptiuon e) {
			return 0;
		}
		if (sumInputs() >= treshold) {
			return inputSum;
		}
		return 0;
	}

	private float sumInputs() {
		float result = 0;
		for (Entry<String,WeightRecord> in : input.feedInput().entrySet()) {
			try {
				result += in.getValue().getWeight()
						* weightSource.getWeight(neuronName, in.getKey());
			} catch (KnowledgeManagementSettingsExceptiuon e) {
				return 0;

			} 
		}
		return result;
	}
}
