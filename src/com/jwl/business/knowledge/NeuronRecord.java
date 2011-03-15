package com.jwl.business.knowledge;

import java.util.Map;

public class NeuronRecord {
	private String neuronName;
	private Map<String, WeightRecord> weights;
	private float threshold;
	public NeuronRecord(String neuronName, Map<String, WeightRecord> weights,
			float threshold) {
		super();
		this.neuronName = neuronName;
		this.weights = weights;
		this.threshold = threshold;
	}
	public String getNeuronName() {
		return neuronName;
	}
	public Map<String, WeightRecord> getWeights() {
		return weights;
	}
	public float getThreshold() {
		return threshold;
	}
	
}
