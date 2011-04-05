package com.jwl.integration.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jwl.business.article.PostTO;
import com.jwl.integration.entity.Post;

public class PostConverter {

	public static Post convertToEntity(PostTO post) {
		Post entity = new Post();
		entity.setCreated(post.getCreated());
		entity.setAuthor(post.getAuthor());
		entity.setText(post.getText());
		return entity;
	}

	public static PostTO convertFromEntity(Post entity) {
		PostTO post = new PostTO();
		post.setAuthor(entity.getAuthor());
		post.setCreated(entity.getCreated());
		post.setId(entity.getId());
		post.setText(entity.getText());
		return post;
	}

	public static List<PostTO> convertFromEntities(List<Post> entities) {
		List<PostTO> result = new ArrayList<PostTO>();
		for (Post entity : entities) {
			PostTO post = convertFromEntity(entity);
			result.add(post);
		}
		orderByDate(result);
		return result;
	} 
	
	public static List<Post> convertToEntites(List<PostTO> posts){
		List<Post> entites = new ArrayList<Post>();
		for(PostTO post :posts){
			Post entity = convertToEntity(post);
			entites.add(entity);
		}
		return entites;
	}

	private static void orderByDate(List<PostTO> list) {
		Collections.sort(list, new Comparator<PostTO>() {
			@Override
			public int compare(PostTO arg0, PostTO arg1) {
				return arg0.getCreated().compareTo(arg1.getCreated());
			}
		});
	}
}
