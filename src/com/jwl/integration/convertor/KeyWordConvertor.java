package com.jwl.integration.convertor;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.KeyWordTO;
import com.jwl.integration.entity.KeyWord;

public class KeyWordConvertor {
	
	public static KeyWord toEntity(KeyWordTO keyWord){
		KeyWord entity = new KeyWord();
		entity.setId(keyWord.getId());
		entity.setWord(keyWord.getWord());
		entity.setWeight(keyWord.getWeight());
		entity.setCreated(keyWord.getCreated());
		return entity;
	}
	
	public static KeyWordTO fromEntity(KeyWord entity){
		KeyWordTO keyWord = new KeyWordTO();
		keyWord.setId(entity.getId());
		keyWord.setWord(entity.getWord());
		keyWord.setWeight(entity.getWeight());
		keyWord.setCreated(entity.getCreated());
		return keyWord;
	}
	
	public static List<KeyWordTO> fromEntities(List<KeyWord> entities){
		List<KeyWordTO> result = new ArrayList<KeyWordTO>();
		for(KeyWord entity:entities){
			result.add(fromEntity(entity));
		}
		return result;
	}
}
