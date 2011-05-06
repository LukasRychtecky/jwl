package com.jwl.business.knowledge.keyword;

import java.util.Map;
import java.util.Set;

public class WordVector {

	public static float computeCosineSimilarity(
			Map<String, Float> wordWeights1, Map<String, Float> wordWeights2,
			Set<String> wordDictionary){
		float[] vector1 = getVector(wordWeights1, wordDictionary);
		float[] vector2 = getVector(wordWeights2, wordDictionary);
		return computeCosineSimilarity(vector1, vector2);
	}

	private static float[] getVector(Map<String, Float> wordWeights,
			Set<String> wordDictionary){
		float[] vector = new float[wordDictionary.size()];
		int i = 0;
		for (String word : wordDictionary){
			Float weight = wordWeights.get(word);
			if(weight == null){
				vector[i] = 0;

			}else{
				vector[i] = weight.floatValue();
			}
			i++;
		}
		return vector;
	}

	private static float computeCosineSimilarity(float[] vector1,
			float[] vector2){
		float numerator = computeScalarProduct(vector1, vector2);
		float vector1Lenght = computeVectorLenght(vector1);
		float vector2Lenght = computeVectorLenght(vector2);
		float denominator = vector1Lenght * vector2Lenght;
		if(denominator == 0F){
			return 0;
		}
		return numerator / denominator;
	}

	private static float computeScalarProduct(float[] vector1, float[] vector2){
		float result = 0;
		for (int i = 0; i < vector1.length; i++){
			result += vector1[i] * vector2[i];
		}
		return result;
	}

	private static float computeVectorLenght(float[] vector){
		float result = 0;
		for (int i = 0; i < vector.length; i++){
			result += vector[i] * vector[i];
		}
		result = (float) Math.sqrt(result);
		return result;
	}
}
