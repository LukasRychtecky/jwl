package com.jwl.integration.convertor;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.TopicTO;
import com.jwl.integration.entity.Topic;

public class TopicConverter {
	
	public static TopicTO convertFromEntity(Topic entity){
		TopicTO topic = new TopicTO();
		topic.setId(entity.getId());
		topic.setTitle(entity.getTitle());
		topic.setClosed(entity.getClosed());
		topic.setPosts(PostConverter.convertFromEntities(entity.getPosts()));
		return topic;
	}
	
	public static Topic convertToEntity(TopicTO topic){
		Topic entity = new Topic();
		entity.setTitle(topic.getTitle());
		entity.setClosed(topic.isClosed());
		return entity;
	}
	
	public static List<TopicTO> convertFromEntities(List<Topic> entities){
		List<TopicTO> topics = new ArrayList<TopicTO>();
		for(Topic entity :entities){
			TopicTO topic = convertFromEntity(entity);
			topics.add(topic);
		}
		return topics;
	}
}
